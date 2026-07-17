package com.example.mubashir;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Server {
    private static final String DB_URL;
    private static final String DB_USER;
    private static final String DB_PASS;

    static {
        Properties props = new Properties();
        try (InputStream input = Server.class.getClassLoader()
                .getResourceAsStream("application-secrets.properties")) {

            if (input == null) {
                throw new RuntimeException(
                        "application-secrets.properties not found on classpath. " +
                        "Copy src/main/resources/application-secrets.properties.example to " +
                        "src/main/resources/application-secrets.properties and fill in your real credentials."
                );
            }
            props.load(input);

        } catch (IOException e) {
            throw new RuntimeException("Failed to load application-secrets.properties", e);
        }

        DB_URL = props.getProperty("db.url");
        DB_USER = props.getProperty("db.user");
        DB_PASS = props.getProperty("db.password");
    }

    private static void fileLoader() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static String hashPassword(String password) {
        return PasswordUtil.hashPassword(password);
    }

    public static boolean isEmailExist(String email) {
        fileLoader();
        try (Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            PreparedStatement ps = c.prepareStatement("SELECT 1 FROM users WHERE email=?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Error checking email: " + e.getMessage());
            return false;
        }
    }

    public static void signUp(String userRole, String name, String email, String phoneNumber, String address, String password) {
        fileLoader();

        String cleanPhoneNumber = phoneNumber.replaceAll("\\D", "");

        try (Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            c.setAutoCommit(false);

            PreparedStatement ps = c.prepareStatement(
                    "INSERT INTO users(user_role, full_name, email, phone, address, password_hash) VALUES (?,?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, userRole);
            ps.setString(2, name);
            ps.setString(3, email);
            ps.setString(4, cleanPhoneNumber);
            ps.setString(5, address);
            ps.setString(6, hashPassword(password));
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            int userId=0;
            if(rs.next()){
                userId=rs.getInt(1);
                }
            PreparedStatement auditStmt = c.prepareStatement(
                    "INSERT INTO audit_log(user_id, action) VALUES (?, ?)"
            );
            auditStmt.setInt(1, userId);
            auditStmt.setString(2, "User registered: " + email);
            auditStmt.executeUpdate();

            c.commit();

        } catch (SQLException ex) {
            System.out.println("SignUp Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static boolean logIn(String email, String password) {
        fileLoader();
        try (Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            PreparedStatement ps = c.prepareStatement(
                    "SELECT user_id, password_hash FROM users WHERE email = ?"
            );
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password_hash");
                if (PasswordUtil.verifyPassword(password, storedHash)){
                    int userId = rs.getInt("user_id");
                    logAudit(userId, "User logged in", c);
                    return true;
                }
            }
        } catch (SQLException ex) {
            System.out.println("Login error: " + ex.getMessage());
        }
        return false;
    }

    public static int getUserId(String email) {
        fileLoader();
        try (Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            PreparedStatement ps = c.prepareStatement(
                    "SELECT user_id FROM users WHERE email = ?"
            );
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt("user_id") : 0;
        } catch (SQLException e) {
            System.out.println("Error getting user ID: " + e.getMessage());
            return 0;
        }
    }

    public static String getUserRole(String email) {
        fileLoader();
        try (Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            PreparedStatement ps = c.prepareStatement(
                    "SELECT user_role FROM users WHERE email = ?"
            );
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getString("user_role") : "";
        } catch (SQLException e) {
            System.out.println("Error getting user role: " + e.getMessage());
            return "";
        }
    }

    public static String getUserEmail(int userId) {
        try (Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            PreparedStatement ps = c.prepareStatement(
                    "SELECT email FROM users WHERE user_id = ?"
            );
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getString("email") : "";
        } catch (SQLException e) {
            System.out.println("Error getting user email: " + e.getMessage());
            return "";
        }
    }

    public static BasicInfo getBasicInfo(int userId) {
        try (Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            PreparedStatement ps = c.prepareStatement(
                    "SELECT full_name, email, phone, address FROM users WHERE user_id = ?"
            );
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new BasicInfo(
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("address"),
                        rs.getString("phone")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error getting basic info: " + e.getMessage());
        }
        return null;
    }

    public static double getWalletAmount(int userId) {
        try (Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            PreparedStatement ps = c.prepareStatement(
                    "SELECT balance FROM wallets WHERE wallet_id = ?"
            );
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getDouble("balance") : 0.0;
        } catch (SQLException ex) {
            System.out.println("Error getting wallet amount: " + ex.getMessage());
            return 0;
        }
    }

    public static void depositAmount(int amount, int userId) {
        try (Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            c.setAutoCommit(false);

            PreparedStatement ps = c.prepareStatement(
                    "UPDATE wallets SET balance = balance + ? WHERE wallet_id = ?"
            );
            ps.setInt(1, amount);
            ps.setInt(2, userId);
            ps.executeUpdate();

            PreparedStatement txStmt = c.prepareStatement(
                    "INSERT INTO transactions(from_user_id, to_user_id, type, amount, description) " +
                            "VALUES (NULL, ?, 'deposit', ?, 'Wallet deposit')"
            );
            txStmt.setInt(1, userId);
            txStmt.setInt(2, amount);
            txStmt.executeUpdate();

            logAudit(userId, "Deposited: " + amount, c);

            c.commit();
            c.setAutoCommit(true);
        } catch (SQLException es) {
            System.out.println("Deposit error: " + es.getMessage());
        }
    }

    public static void withdrawAmount(int amount, int userId) {
        try (Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            c.setAutoCommit(false);

            if (getWalletAmount(userId) < amount) {
                throw new SQLException("Insufficient balance");
            }

            PreparedStatement ps = c.prepareStatement(
                    "UPDATE wallets SET balance = balance - ? WHERE wallet_id = ?"
            );
            ps.setInt(1, amount);
            ps.setInt(2, userId);
            ps.executeUpdate();

            PreparedStatement txStmt = c.prepareStatement(
                    "INSERT INTO transactions(from_user_id, to_user_id, type, amount, description) " +
                            "VALUES (?, NULL, 'withdrawal', ?, 'Wallet withdrawal')"
            );
            txStmt.setInt(1, userId);
            txStmt.setInt(2, amount);
            txStmt.executeUpdate();

            logAudit(userId, "Withdrew: " + amount, c);

            c.commit();
            c.setAutoCommit(true);
        } catch (SQLException es) {
            System.out.println("Withdrawal error: " + es.getMessage());
        }
    }


    public static void submitBorrowForm(int amount, int tenure, int userId) {
        try (Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            c.setAutoCommit(false);

            try {
                String loanInsertSQL = "INSERT INTO Loans (borrower_id, loan_amount, base_interest_rate, tenure_months) " +
                        "VALUES (?,?,?,?)";

                try (PreparedStatement ps = c.prepareStatement(loanInsertSQL, Statement.RETURN_GENERATED_KEYS)) {

                    ps.setInt(1, userId);
                    ps.setInt(2, amount);
                    ps.setDouble(3, baseInterestRate());
                    ps.setInt(4, tenure);
                    int rowsAffected = ps.executeUpdate();

                    if (rowsAffected == 0) {
                        throw new SQLException("Creating loan failed, no rows affected.");
                    }

                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        int loanId = 0;
                        if (rs.next()){
                            loanId=rs.getInt(1);
                            System.out.println("Successfully created loan ID: " + loanId);
                        } else {
                            throw new SQLException("Creating loan failed, no ID obtained.");
                        }
                    }
                }

                // Log audit
                logAudit(userId, "Applied for loan: " + amount + " for " + tenure + " months", c);

                c.commit();

            } catch (SQLException ex) {
                c.rollback();
                throw ex;
            }

        } catch (SQLException ex) {
            System.out.println("Loan application error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }


    public static void submitInvestmentForm(int amount, int userId) {
        try (Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            c.setAutoCommit(false);

            PreparedStatement ps = c.prepareStatement(
                    "INSERT INTO pool_contribution(investor_id, amount) VALUES (?, ?)"
            );
            ps.setInt(1, userId);
            ps.setInt(2, amount);
            ps.executeUpdate();

            PreparedStatement walletStmt = c.prepareStatement(
                    "UPDATE wallets SET balance = balance - ? WHERE wallet_id = ?"
            );
            walletStmt.setInt(1, amount);
            walletStmt.setInt(2, userId);
            walletStmt.executeUpdate();

            PreparedStatement txStmt = c.prepareStatement(
                    "INSERT INTO transactions(from_user_id, to_user_id, type, amount, description) " +
                            "VALUES (?, NULL, 'loan_disbursement', ?, 'Investment contribution')"
            );
            txStmt.setInt(1, userId);
            txStmt.setInt(2, amount);
            txStmt.executeUpdate();

            logAudit(userId, "Invested: " + amount, c);
            c.commit();
            c.setAutoCommit(true);
        } catch (SQLException e) {
            System.out.println("Investment error: " + e.getMessage());
        }
    }


    public static void processLoanRepayment(int loanId, int amount) {
        try (Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            c.setAutoCommit(false);

            PreparedStatement loanStmt = c.prepareStatement(
                    "SELECT borrower_id, loan_amount, base_interest_rate FROM loans WHERE loan_id = ?"
            );
            loanStmt.setInt(1, loanId);
            ResultSet loanRs = loanStmt.executeQuery();

            if (!loanRs.next()) return;

            int borrowerId = loanRs.getInt("borrower_id");
            int loanAmount = loanRs.getInt("loan_amount");
            float interestRate = loanRs.getFloat("base_interest_rate");

            float interest = loanAmount * (interestRate / 100);
            float platformFee = interest * 0.25f;
            float investorShare = interest - platformFee;

            PreparedStatement repaymentStmt = c.prepareStatement("INSERT INTO Loan_Repayment(loan_id, Loan_given_at, amount) " +
                            "VALUES (?, NOW(), ?)");
            repaymentStmt.setInt(1, loanId);
            repaymentStmt.setInt(2, amount);
            repaymentStmt.executeUpdate();


            if (amount >= loanAmount + interest) {
                PreparedStatement updateStmt = c.prepareStatement(
                        "UPDATE loans SET status = 'repaid' WHERE loan_id = ?"
                );
                updateStmt.setInt(1, loanId);
                updateStmt.executeUpdate();
            }

            distributeInterestToInvestors(loanId, investorShare, c);

            collectPlatformFee(platformFee, loanId, c);


            PreparedStatement borrowerStmt = c.prepareStatement(
                    "UPDATE wallets SET balance = balance - ? WHERE wallet_id = ?"
            );
            borrowerStmt.setInt(1, amount);
            borrowerStmt.setInt(2, borrowerId);
            borrowerStmt.executeUpdate();

            PreparedStatement txStmt = c.prepareStatement(
                    "INSERT INTO transactions(from_user_id, to_user_id, type, amount, loan_id, description) " +
                            "VALUES (?, NULL, 'repayment', ?, ?, 'Loan repayment')"
            );

            txStmt.setInt(1, borrowerId);
            txStmt.setInt(2, amount);
            txStmt.setInt(3, loanId);
            txStmt.executeUpdate();

            c.commit();
            c.setAutoCommit(true);

        } catch (SQLException e) {
            System.out.println("Repayment error: " + e.getMessage());
        }
    }

    private static void distributeInterestToInvestors(int loanId, float totalInterest, Connection c) throws SQLException {
        PreparedStatement ps = c.prepareStatement(
                "SELECT pc.investor_id, lf.amount_used, " +
                        "SUM(lf.amount_used) OVER() as total_funded " +
                        "FROM loan_funding lf " +
                        "JOIN pool_contribution pc ON lf.contribution_id = pc.contribution_id " +
                        "WHERE lf.loan_id = ?"
        );
        ps.setInt(1, loanId);
        ResultSet rs = ps.executeQuery();

        float totalFunded = 0;
        List<InvestorShare> shares = new ArrayList<>();

        while (rs.next()) {
            int investorId = rs.getInt("investor_id");
            float amountUsed = rs.getFloat("amount_used");
            totalFunded = rs.getFloat("total_funded");

            shares.add(new InvestorShare(investorId, amountUsed));
        }


        for (InvestorShare share : shares) {
            float investorInterest = (share.amountUsed / totalFunded) * totalInterest;


            PreparedStatement payoutStmt = c.prepareStatement(
                    "INSERT INTO Interest_Payout(loan_id, investor_id, principal_share, " +
                            "interest_earned, platform_fee) VALUES (?, ?, ?, ?, 0)"
            );
            payoutStmt.setInt(1, loanId);
            payoutStmt.setInt(2, share.investorId);
            payoutStmt.setFloat(3, share.amountUsed);
            payoutStmt.setFloat(4, investorInterest);
            payoutStmt.executeUpdate();

            // Update investor wallet
            PreparedStatement walletStmt = c.prepareStatement(
                    "UPDATE wallets SET balance = balance + ? WHERE wallet_id = ?"
            );
            walletStmt.setFloat(1, investorInterest);
            walletStmt.setInt(2, share.investorId);
            walletStmt.executeUpdate();

            // Record transaction
            PreparedStatement txStmt = c.prepareStatement(
                    "INSERT INTO transactions(from_user_id, to_user_id, type, amount, " +
                            "loan_id, description) VALUES (NULL, ?, 'interest_payout', ?, ?, 'Interest payment')"
            );
            txStmt.setInt(1, share.investorId);
            txStmt.setFloat(2, investorInterest);
            txStmt.setInt(3, loanId);
            txStmt.executeUpdate();
        }
    }

    private static void collectPlatformFee(float platformFee, int loanId, Connection c) throws SQLException {
        int platformAccountId = 1;

        PreparedStatement walletStmt = c.prepareStatement(
                "UPDATE wallets SET balance = balance + ? WHERE wallet_id = ?"
        );
        walletStmt.setFloat(1, platformFee);
        walletStmt.setInt(2, platformAccountId);
        walletStmt.executeUpdate();

        PreparedStatement txStmt = c.prepareStatement(
                "INSERT INTO transactions(from_user_id, to_user_id, type, amount, " +
                        "loan_id, description) VALUES (NULL, ?, 'platform_fee', ?, ?, 'Platform fee')"
        );
        txStmt.setInt(1, platformAccountId);
        txStmt.setFloat(2, platformFee);
        txStmt.setInt(3, loanId);
        txStmt.executeUpdate();
    }

    public static List<LoanSummary> getActiveLoans(int userId) {
        List<LoanSummary> loans = new ArrayList<>();
        try (Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            PreparedStatement ps = c.prepareStatement(
                    "SELECT loan_id, loan_amount, base_interest_rate, tenure_months, " +
                            "status, due_date FROM loans WHERE borrower_id = ? AND status = 'Active'"
            );
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                loans.add(new LoanSummary(
                        rs.getInt("loan_id"),
                        rs.getInt("loan_amount"),
                        rs.getFloat("base_interest_rate"),
                        rs.getInt("tenure_months"),
                        rs.getString("status"),
                        rs.getTimestamp("due_date")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error getting active loans: " + e.getMessage());
        }
        return loans;
    }

    public static List<InvestmentSummary> getInvestorPayouts(int userId) {
        List<InvestmentSummary> payouts = new ArrayList<>();
        try (Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            PreparedStatement ps = c.prepareStatement(
                    "SELECT ip.payout_id, ip.loan_id, ip.principal_share, " +
                            "ip.interest_earned, ip.platform_fee, ip.paid_at " +
                            "FROM interest_payout ip " +
                            "WHERE ip.investor_id = ? ORDER BY ip.paid_at DESC"
            );
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                payouts.add(new InvestmentSummary(
                        rs.getInt("payout_id"),
                        rs.getInt("loan_id"),
                        rs.getFloat("principal_share"),
                        rs.getFloat("interest_earned"),
                        rs.getFloat("platform_fee"),
                        rs.getTimestamp("paid_at")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error getting investor payouts: " + e.getMessage());
        }
        return payouts;
    }

    public static double baseInterestRate(){
        try(Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            PreparedStatement ps = c.prepareStatement(
                    "select setting_value from settings where setting_key = 'default_interest_rate';");
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getDouble("setting_value");
        }catch (SQLException e) {return 0;}
    }

    private static void logAudit(int userId, String action, Connection c) throws SQLException {
        PreparedStatement ps = c.prepareStatement(
                "INSERT INTO audit_log(user_id, action) VALUES (?, ?)"
        );
        ps.setInt(1, userId);
        ps.setString(2, action);
        ps.executeUpdate();
    }

    public static List<String> getBorrowerFormSubmitMessagelist(int userId) {
        List<String> list = new ArrayList<>();
        try (Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            PreparedStatement ps = c.prepareStatement(
                    "SELECT loan_id, loan_amount, tenure_months, status FROM loans WHERE borrower_id = ?"
            );
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String message = String.format(
                        "Loan #%d: Amount: $%d, Tenure: %d months, Status: %s",
                        rs.getInt("loan_id"),
                        rs.getInt("loan_amount"),
                        rs.getInt("tenure_months"),
                        rs.getString("status")
                );
                list.add(message);
            }
        } catch (SQLException e) {
            System.out.println("Error getting borrower forms: " + e.getMessage());
        }
        return list;
    }

    public static List<String> getInvestmentFormSubmitMessagelist(int userId) {
        List<String> list = new ArrayList<>();
        try (Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            PreparedStatement ps = c.prepareStatement(
                    "SELECT contribution_id, amount, contributed_at FROM pool_contribution " +
                            "WHERE investor_id = ? ORDER BY contributed_at DESC"
            );
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String message = String.format(
                        "Investment #%d: $%d on %s",
                        rs.getInt("contribution_id"),
                        rs.getInt("amount"),
                        rs.getTimestamp("contributed_at").toString()
                );
                list.add(message);
            }
        } catch (SQLException e) {
            System.out.println("Error getting investment forms: " + e.getMessage());
        }
        return list;
    }

    private static class InvestorShare {
        int investorId;
        float amountUsed;

        InvestorShare(int investorId, float amountUsed) {
            this.investorId = investorId;
            this.amountUsed = amountUsed;
        }
    }
}