package Admin;

public class WalletInfo {
        int userId;
        String userName;
        String userRole;
        double balance;
        String lastTransaction;

        public WalletInfo(int userId, String userName, String userRole, double balance, String lastTransaction) {
            this.userId = userId;
            this.userName = userName;
            this.userRole = userRole;
            this.balance = balance;
            this.lastTransaction = lastTransaction;
        }
    }
