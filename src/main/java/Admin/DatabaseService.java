package Admin;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public  class DatabaseService {
        private static String DB_URL;
        private static String DB_USER;
        private static String DB_PASS;

        public static void initialize(String url, String user, String pass) {
            DB_URL = url;
            DB_USER = user;
            DB_PASS = pass;

            // Test connection on initialization
            try {
                Connection conn = getConn();
                System.out.println("Database connection established successfully!");
                conn.close();
            } catch (SQLException e) {
                System.err.println("Failed to connect to database: " + e.getMessage());
                e.printStackTrace();
            }
        }

        public static boolean testConnection() {
            try (Connection conn = getConn()) {
                return conn != null && !conn.isClosed();
            } catch (SQLException e) {
                return false;
            }
        }

        private static Connection getConn() throws SQLException {
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        }

        private static void ensureWallet(Connection c, int userId) throws SQLException {
            String sql = "INSERT INTO Wallets (wallet_id, balance) VALUES (?, 0.00) ON DUPLICATE KEY UPDATE wallet_id = wallet_id";
            try (PreparedStatement p = c.prepareStatement(sql)) {
                p.setInt(1, userId);
                p.executeUpdate();
            }
        }

        private static void creditWallet(Connection c, int userId, double amount) throws SQLException {
            ensureWallet(c, userId);
            String upd = "UPDATE Wallets SET balance = balance + ? WHERE wallet_id = ?";
            try (PreparedStatement p = c.prepareStatement(upd)) {
                p.setDouble(1, amount);
                p.setInt(2, userId);
                p.executeUpdate();
            }
        }

        private static void debitWallet(Connection c, int userId, double amount) throws SQLException {
            ensureWallet(c, userId);
            String upd = "UPDATE Wallets SET balance = balance - ? WHERE wallet_id = ?";
            try (PreparedStatement p = c.prepareStatement(upd)) {
                p.setDouble(1, amount);
                p.setInt(2, userId);
                p.executeUpdate();
            }
        }

        public static int count(String table) {
            String sql = "select count(*) from " + table;
            try (Connection c = getConn(); Statement s = c.createStatement(); ResultSet rs = s.executeQuery(sql)) {
                if (rs.next()) return rs.getInt(1);
            } catch (Exception ex) { ex.printStackTrace(); }
            return 0;
        }

        public static int countByRole(String role) {
            String sql = "select count(*) from users where user_role = ?";
            try (Connection c = getConn(); PreparedStatement p = c.prepareStatement(sql)) {
                p.setString(1, role);
                try (ResultSet rs = p.executeQuery()) {
                    if (rs.next()) return rs.getInt(1);
                }
            } catch (Exception ex) { ex.printStackTrace(); }
            return 0;
        }

        public static Number countCondition(String table, String condition) {
            String sql = "select count(*) from " + table + " where " + condition;
            try (Connection c = getConn(); Statement s = c.createStatement(); ResultSet rs = s.executeQuery(sql)) {
                if (rs.next()) return rs.getInt(1);
            } catch (Exception ex) { ex.printStackTrace(); }
            return 0;
        }

        public static Number sum(String table, String field) {
            String sql = "select sum(" + field + ") from " + table;
            try (Connection c = getConn(); Statement s = c.createStatement(); ResultSet rs = s.executeQuery(sql)) {
                if (rs.next()) return rs.getDouble(1);
            } catch (Exception ex) { ex.printStackTrace(); }
            return 0.0;
        }
        public static Number platformWallet(){
            String sql = "select balance from wallets where wallet_id =1";
            try (Connection c = getConn(); Statement s = c.createStatement(); ResultSet rs = s.executeQuery(sql)) {
                if (rs.next()) return rs.getDouble(1);
            } catch (Exception ex) { ex.printStackTrace(); }
            return 0.0;
        }

        public static Double getWalletBalance(int userId) {
            String sql = "SELECT balance FROM Wallets WHERE wallet_id = ?";
            try (Connection c = getConn(); PreparedStatement p = c.prepareStatement(sql)) {
                p.setInt(1, userId);
                try (ResultSet rs = p.executeQuery()) {
                    if (rs.next()) {
                        return rs.getDouble("balance");
                    }
                }
            } catch (Exception ex) { ex.printStackTrace(); }
            return 0.0;
        }

        public static List<WalletInfo> loadAllWallets() {
            List<WalletInfo> out = new ArrayList<>();
            String sql = "SELECT u.user_id, u.full_name, u.user_role, COALESCE(w.balance, 0) as balance, " +
                    "(SELECT MAX(created_at) FROM transactions t WHERE t.from_user_id = u.user_id OR t.to_user_id = u.user_id) as last_txn " +
                    "FROM users u LEFT JOIN wallets w ON u.user_id = w.wallet_id ORDER BY u.user_id";
            try (Connection c = getConn(); Statement s = c.createStatement(); ResultSet rs = s.executeQuery(sql)) {
                while (rs.next()) {
                    out.add(new WalletInfo(
                            rs.getInt("user_id"),
                            rs.getString("full_name"),
                            rs.getString("user_role"),
                            rs.getDouble("balance"),
                            rs.getTimestamp("last_txn") != null ? rs.getTimestamp("last_txn").toString() : "Never"
                    ));
                }
            } catch (Exception ex) { ex.printStackTrace(); }
            return out;
        }

        public static List<User> loadUsers() {
            List<User> out = new ArrayList<>();
            String sql = "select user_id, user_role, full_name, email, phone, address, kyc_verified, created_at from users order by created_at desc";
            try (Connection c = getConn(); Statement s = c.createStatement(); ResultSet rs = s.executeQuery(sql)) {
                while (rs.next()) {
                    out.add(new User(rs.getInt("user_id"), rs.getString("user_role"), rs.getString("full_name"),
                            rs.getString("email"), rs.getString("phone"), rs.getString("address"),
                            rs.getBoolean("kyc_verified"), rs.getTimestamp("created_at")));
                }
            } catch (Exception ex) { ex.printStackTrace(); }
            return out;
        }

        public static List<User> loadUsersByRole(String role) {
            List<User> out = new ArrayList<>();
            String sql = "select user_id, user_role, full_name, email, phone, address, kyc_verified, created_at from users where user_role=? order by created_at desc";
            try (Connection c = getConn(); PreparedStatement p = c.prepareStatement(sql)) {
                p.setString(1, role);
                try (ResultSet rs = p.executeQuery()) {
                    while (rs.next()) {
                        out.add(new User(rs.getInt("user_id"), rs.getString("user_role"), rs.getString("full_name"),
                                rs.getString("email"), rs.getString("phone"), rs.getString("address"),
                                rs.getBoolean("kyc_verified"), rs.getTimestamp("created_at")));
                    }
                }
            } catch (Exception ex) { ex.printStackTrace(); }
            return out;
        }

        public static boolean updateKyc(int userId, boolean val) {
            String sql = "update users set kyc_verified=? where user_id=?";
            try (Connection c = getConn(); PreparedStatement p = c.prepareStatement(sql)) {
                p.setBoolean(1, val);
                p.setInt(2, userId);
                return p.executeUpdate() > 0;
            } catch (Exception ex) { ex.printStackTrace(); return false; }
        }

        public static boolean deleteUser(int userId) {
            String sql = "delete from users where user_id=?";
            try (Connection c = getConn(); PreparedStatement p = c.prepareStatement(sql)) {
                p.setInt(1, userId);
                return p.executeUpdate() > 0;
            } catch (Exception ex) { ex.printStackTrace(); return false; }
        }

        public static List<PoolContribution> loadContributions() {
            List<PoolContribution> out = new ArrayList<>();
            String sql = "select contribution_id, investor_id, amount, contributed_at from Pool_Contribution order by contributed_at desc";
            try (Connection c = getConn(); Statement s = c.createStatement(); ResultSet rs = s.executeQuery(sql)) {
                while (rs.next()) out.add(new PoolContribution(rs.getInt("contribution_id"), rs.getInt("investor_id"), rs.getDouble("amount"), rs.getTimestamp("contributed_at")));
            } catch (Exception ex) { ex.printStackTrace(); }
            return out;
        }

        public static List<ContributionBalance> loadContributionBalances() {
            List<ContributionBalance> out = new ArrayList<>();
            String sql = "select contribution_id, investor_id, contributed_amount, amount_allocated, remaining_amount, contributed_at from v_contribution_balance order by contributed_at desc";
            try (Connection c = getConn(); Statement s = c.createStatement(); ResultSet rs = s.executeQuery(sql)) {
                while (rs.next()) {
                    out.add(new ContributionBalance(
                            rs.getInt("contribution_id"),
                            rs.getInt("investor_id"),
                            rs.getDouble("contributed_amount"),
                            rs.getDouble("amount_allocated"),
                            rs.getDouble("remaining_amount"),
                            rs.getTimestamp("contributed_at")
                    ));
                }
            } catch (Exception ex) { ex.printStackTrace(); }
            return out;
        }

        public static boolean createLoanFunding(Connection c, int loanId, int contributionId, double amount) throws SQLException {
            String sql = "insert into Loan_Funding (loan_id, contribution_id, amount_used) values (?, ?, ?)";
            try (PreparedStatement p = c.prepareStatement(sql)) {
                p.setInt(1, loanId);
                p.setInt(2, contributionId);
                p.setDouble(3, amount);
                return p.executeUpdate() > 0;
            }
        }


        public static List<Loan> loadLoans() {
            List<Loan> out = new ArrayList<>();
            String sql = "select loan_id, borrower_id, loan_amount, base_interest_rate, tenure_months, status, due_date, created_at from Loans order by created_at desc";
            try (Connection c = getConn(); Statement s = c.createStatement(); ResultSet rs = s.executeQuery(sql)) {
                while (rs.next()) {
                    out.add(new Loan(rs.getInt("loan_id"), rs.getInt("borrower_id"), rs.getDouble("loan_amount"), rs.getDouble("base_interest_rate"), rs.getInt("tenure_months"), rs.getString("status"), rs.getDate("due_date"), rs.getTimestamp("created_at")));
                }
            } catch (Exception ex) { ex.printStackTrace(); }
            return out;
        }

        private static Loan getLoan(Connection c, int loanId) throws SQLException {
            String sql = "select loan_id, borrower_id, loan_amount, base_interest_rate, tenure_months, status, due_date, created_at from Loans where loan_id=?";
            try (PreparedStatement p = c.prepareStatement(sql)) {
                p.setInt(1, loanId);
                try (ResultSet rs = p.executeQuery()) {
                    if (rs.next()) {
                        return new Loan(
                                rs.getInt("loan_id"),
                                rs.getInt("borrower_id"),
                                rs.getDouble("loan_amount"),
                                rs.getDouble("base_interest_rate"),
                                rs.getInt("tenure_months"),
                                rs.getString("status"),
                                rs.getDate("due_date"),
                                rs.getTimestamp("created_at")
                        );
                    }
                }
            }
            return null;
        }

        public static boolean markLoanRepaid(int loanId) {
            try (Connection c = getConn()) {
                c.setAutoCommit(false);

                String upd = "update Loans set status='repaid' where loan_id=?";
                try (PreparedStatement p = c.prepareStatement(upd)) {
                    p.setInt(1, loanId);
                    p.executeUpdate();
                }

                Loan loan = getLoan(c, loanId);
                if (loan != null) {
                    String txnSql = "INSERT INTO transactions (from_user_id, to_user_id, type, amount, loan_id, description) VALUES (?, ?, 'repayment', ?, ?, ?)";
                    try (PreparedStatement p = c.prepareStatement(txnSql)) {
                        p.setInt(1, loan.borrowerId);
                        p.setObject(2, null);
                        p.setDouble(3, loan.loanAmount);
                        p.setInt(4, loanId);
                        p.setString(5, "Loan repayment");
                        p.executeUpdate();
                    }
                }

                c.commit();
                return true;
            } catch (Exception ex) { ex.printStackTrace(); return false; }
        }

        // Repayments
        public static List<LoanRepayment> loadRepaymentsForLoan(int loanId) {
            List<LoanRepayment> out = new ArrayList<>();
            String sql = "select repayment_id, loan_id, Loan_given_at, amount, repaid_at from Loan_Repayment where loan_id=?";
            try (Connection c = getConn(); PreparedStatement p = c.prepareStatement(sql)) {
                p.setInt(1, loanId);
                try (ResultSet rs = p.executeQuery()) {
                    while (rs.next()) out.add(new LoanRepayment(rs.getInt("repayment_id"), rs.getInt("loan_id"), rs.getTimestamp("Loan_given_at"), rs.getDouble("amount"), rs.getTimestamp("repaid_at")));
                }
            } catch (Exception ex) { ex.printStackTrace(); }
            return out;
        }

        public static List<TransactionRow> loadTransactions() {
            List<TransactionRow> out = new ArrayList<>();
            String sql = "select transaction_id, from_user_id, to_user_id, type, amount, loan_id, description, created_at from transactions order by created_at desc";
            try (Connection c = getConn(); Statement s = c.createStatement(); ResultSet rs = s.executeQuery(sql)) {
                while (rs.next()) out.add(new TransactionRow(rs.getInt("transaction_id"), rs.getObject("from_user_id") == null ? null : rs.getInt("from_user_id"),
                        rs.getObject("to_user_id") == null ? null : rs.getInt("to_user_id"), rs.getString("type"), rs.getDouble("amount"),
                        rs.getObject("loan_id") == null ? null : rs.getInt("loan_id"),  rs.getString("description"), rs.getTimestamp("created_at")));
            } catch (Exception ex) { ex.printStackTrace(); }
            return out;
        }

        public static List<TransactionRow> loadTransactionsByType(String type) {
            List<TransactionRow> out = new ArrayList<>();
            String sql = "select transaction_id, from_user_id, to_user_id, type, amount, loan_id, description, created_at from transactions where type=? order by created_at desc";
            try (Connection c = getConn(); PreparedStatement p = c.prepareStatement(sql)) {
                p.setString(1, type);
                try (ResultSet rs = p.executeQuery()) {
                    while (rs.next()) out.add(new TransactionRow(rs.getInt("transaction_id"), rs.getObject("from_user_id") == null ? null : rs.getInt("from_user_id"),
                            rs.getObject("to_user_id") == null ? null : rs.getInt("to_user_id"), rs.getString("type"), rs.getDouble("amount"),
                            rs.getObject("loan_id") == null ? null : rs.getInt("loan_id"), rs.getString("description"), rs.getTimestamp("created_at")));
                }
            } catch (Exception ex) { ex.printStackTrace(); }
            return out;
        }

        public static List<TransactionRow> loadTransactionsForUser(int userId) {
            List<TransactionRow> out = new ArrayList<>();
            String sql = "select transaction_id, from_user_id, to_user_id, type, amount, loan_id, description, created_at from transactions where from_user_id=? or to_user_id=? order by created_at desc";
            try (Connection c = getConn(); PreparedStatement p = c.prepareStatement(sql)) {
                p.setInt(1, userId);
                p.setInt(2, userId);
                try (ResultSet rs = p.executeQuery()) {
                    while (rs.next()) out.add(new TransactionRow(rs.getInt("transaction_id"), rs.getObject("from_user_id") == null ? null : rs.getInt("from_user_id"),
                            rs.getObject("to_user_id") == null ? null : rs.getInt("to_user_id"), rs.getString("type"), rs.getDouble("amount"),
                            rs.getObject("loan_id") == null ? null : rs.getInt("loan_id"), rs.getString("description"), rs.getTimestamp("created_at")));
                }
            } catch (Exception ex) { ex.printStackTrace(); }
            return out;
        }

        // Audit
        public static void writeAudit(int userId, String action) {
            String sql = "insert into Audit_Log (user_id, action, ip_address) values (?, ?, ?)";
            try (Connection c = getConn(); PreparedStatement p = c.prepareStatement(sql)) {
                p.setObject(1, userId == 0 ? null : userId);
                p.setString(2, action);
                p.setString(3, "127.0.0.1");
                p.executeUpdate();
            } catch (Exception ex) { ex.printStackTrace(); }
        }

        public static List<AuditLog> loadAuditLogs() {
            List<AuditLog> out = new ArrayList<>();
            String sql = "select log_id, user_id, action, ip_address, timestamp from Audit_Log order by timestamp desc";
            try (Connection c = getConn(); Statement s = c.createStatement(); ResultSet rs = s.executeQuery(sql)) {
                while (rs.next()) out.add(new AuditLog(rs.getInt("log_id"), rs.getObject("user_id") == null ? null : rs.getInt("user_id"), rs.getString("action"), rs.getString("ip_address"), rs.getTimestamp("timestamp")));
            } catch (Exception ex) { ex.printStackTrace(); }
            return out;
        }

        public static List<Setting> loadSettings() {
            List<Setting> out = new ArrayList<>();
            String sql = "select setting_key, setting_value from Settings";
            try (Connection c = getConn(); Statement s = c.createStatement(); ResultSet rs = s.executeQuery(sql)) {
                while (rs.next()) out.add(new Setting(rs.getString("setting_key"), rs.getString("setting_value")));
            } catch (Exception ex) { ex.printStackTrace(); }
            return out;
        }

        public static boolean insertSetting(String key, String value) {
            String sql = "insert into Settings (setting_key, setting_value) values (?, ?)";
            try (Connection c = getConn(); PreparedStatement p = c.prepareStatement(sql)) {
                p.setString(1, key);
                p.setString(2, value);
                return p.executeUpdate() > 0;
            } catch (Exception ex) { ex.printStackTrace(); return false; }
        }

        public static boolean updateSetting(String key, String value) {
            String sql = "update Settings set setting_value=? where setting_key=?";
            try (Connection c = getConn(); PreparedStatement p = c.prepareStatement(sql)) {
                p.setString(1, value);
                p.setString(2, key);
                return p.executeUpdate() > 0;
            } catch (Exception ex) { ex.printStackTrace(); return false; }
        }

        public static boolean deleteSetting(String key) {
            String sql = "delete from Settings where setting_key=?";
            try (Connection c = getConn(); PreparedStatement p = c.prepareStatement(sql)) {
                p.setString(1, key);
                return p.executeUpdate() > 0;
            } catch (Exception ex) { ex.printStackTrace(); return false; }
        }

        public static boolean clearOldAuditLogs() {
            String sql = "DELETE FROM Audit_Log WHERE timestamp < DATE_SUB(NOW(), INTERVAL 30 DAY)";
            try (Connection c = getConn(); PreparedStatement p = c.prepareStatement(sql)) {
                int deleted = p.executeUpdate();
                writeAudit(0, "Cleared old audit logs (" + deleted + " records)");
                return deleted >= 0;
            } catch (Exception ex) {
                ex.printStackTrace();
                return false;
            }
        }

        public static boolean loadDefaultSettings() {
            try (Connection c = getConn()) {
                c.setAutoCommit(false);

                String clearSql = "DELETE FROM Settings";
                try (PreparedStatement p = c.prepareStatement(clearSql)) {
                    p.executeUpdate();
                }

                String[][] defaultSettings = {
                        {"platform_fee_percentage", "25"},
                        {"default_interest_rate", "4.0"},
                        {"minimum_loan_amount", "1000"},
                        {"maximum_loan_amount", "50000"},
                        {"minimum_investment", "1000"},
                        {"currency", "PKR"}
                };

                String insertSql = "INSERT INTO Settings (setting_key, setting_value) VALUES (?, ?)";
                try (PreparedStatement p = c.prepareStatement(insertSql)) {
                    for (String[] setting : defaultSettings) {
                        p.setString(1, setting[0]);
                        p.setString(2, setting[1]);
                        p.addBatch();
                    }
                    p.executeBatch();
                }

                c.commit();
                writeAudit(0, "Loaded default system settings");
                return true;
            } catch (Exception ex) {
                ex.printStackTrace();
                return false;
            }
        }

        public static String getLastSettingUpdate() {
            String sql = "SELECT action, timestamp FROM Audit_Log WHERE action LIKE '%setting%' ORDER BY timestamp DESC LIMIT 1";
            try (Connection c = getConn(); Statement s = c.createStatement(); ResultSet rs = s.executeQuery(sql)) {
                if (rs.next()) {
                    return rs.getString("action") + " at " + rs.getTimestamp("timestamp");
                }
            } catch (Exception ex) { ex.printStackTrace(); }
            return "Never";
        }
        // ---------------- Auto-approval + auto-funding + wallet credit ----------------

        public static ApprovalResult approveAndAutoFundLoan(int loanId) {
            ApprovalResult result = new ApprovalResult();
            try (Connection c = getConn()) {
                c.setAutoCommit(false);

                Loan loan = getLoan(c, loanId);
                if (loan == null) {
                    result.success = false;
                    result.message = "Loan not found.";
                    c.rollback();
                    return result;
                }

                // Update loan status and due date
                {
                    String upd = "update Loans set status='Active', due_date=? where loan_id=?";
                    java.sql.Date due = java.sql.Date.valueOf(LocalDate.now().plusMonths(loan.tenureMonths));
                    try (PreparedStatement p = c.prepareStatement(upd)) {
                        p.setDate(1, due);
                        p.setInt(2, loanId);
                        p.executeUpdate();
                    }
                }

                double alreadyFunded = 0.0;
                String qFunded = "select COALESCE(sum(amount_used),0) from Loan_Funding where loan_id=?";
                try (PreparedStatement p = c.prepareStatement(qFunded)) {
                    p.setInt(1, loanId);
                    try (ResultSet rs = p.executeQuery()) {
                        if (rs.next()) alreadyFunded = rs.getDouble(1);
                    }
                }

                double toFund = Math.max(0.0, loan.loanAmount - alreadyFunded);
                if (toFund <= 0.0001) {
                    result.success = true;
                    result.fundedAmount = 0.0;
                    result.remainingToFund = 0.0;
                    result.message = "Loan already fully funded.";
                    c.commit();
                    return result;
                }

                List<ContributionBalance> balances = new ArrayList<>();
                String qBal = "select contribution_id, investor_id, contributed_amount, amount_allocated, remaining_amount, contributed_at " +
                        "from v_contribution_balance where remaining_amount > 0 order by contributed_at asc";
                try (PreparedStatement p = c.prepareStatement(qBal)) {
                    try (ResultSet rs = p.executeQuery()) {
                        while (rs.next()) {
                            balances.add(new ContributionBalance(
                                    rs.getInt("contribution_id"),
                                    rs.getInt("investor_id"),
                                    rs.getDouble("contributed_amount"),
                                    rs.getDouble("amount_allocated"),
                                    rs.getDouble("remaining_amount"),
                                    rs.getTimestamp("contributed_at")
                            ));
                        }
                    }
                }

                if (balances.isEmpty()) {
                    result.success = false;
                    result.message = "No available contributions to fund this loan.";
                    c.rollback();
                    return result;
                }

                double funded = 0.0;
                for (ContributionBalance cb : balances) {
                    if (funded >= toFund - 0.0001) break;
                    double use = Math.min(cb.remainingAmount, toFund - funded);
                    if (use <= 0.0001) continue;

                    boolean ok = createLoanFunding(c, loanId, cb.contributionId, use);
                    if (!ok) {
                        result.success = false;
                        result.message = "Failed to insert Loan_Funding entry.";
                        c.rollback();
                        return result;
                    }
                    funded += use;
                    result.allocations.add(new AllocationDetail(cb.contributionId, use));
                }

                // CRITICAL: Credit borrower's wallet with the funded amount
                if (funded > 0.0001) {
                    creditWallet(c, loan.borrowerId, funded);

                    String insTxn = "insert into transactions (from_user_id, to_user_id, type, amount, loan_id, description) values (?, ?, 'loan_disbursement', ?, ?, ?)";
                    try (PreparedStatement p = c.prepareStatement(insTxn)) {
                        p.setObject(1, null); // From pool
                        p.setInt(2, loan.borrowerId);
                        p.setDouble(3, funded);
                        p.setInt(4, loanId);
                        p.setString(5, "Auto-funding disbursement from pool contributions");
                        p.executeUpdate();
                    }
                }

                result.success = true;
                result.fundedAmount = funded;
                result.remainingToFund = Math.max(0.0, toFund - funded);
                result.message = "Approved and funded.";
                c.commit();
                return result;

            } catch (Exception ex) {
                ex.printStackTrace();
                result.success = false;
                result.message = "Exception: " + ex.getMessage();
                return result;
            }
        }
    }