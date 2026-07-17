package Admin;

import java.util.ArrayList;
import java.util.List;

public class ApprovalResult {
    boolean success;
    String message;
    double fundedAmount;
    double remainingToFund;
    List<AllocationDetail> allocations = new ArrayList<>();

    static class WalletInfo {
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
}