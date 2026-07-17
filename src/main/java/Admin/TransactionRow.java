package Admin;

import java.sql.Timestamp;

public class TransactionRow {
        int transactionId;
        Integer fromUserId;
        Integer toUserId;
        String type;
        double amount;
        Integer loanId;
        String description;
        Timestamp createdAt;

        public TransactionRow(int transactionId, Integer fromUserId, Integer toUserId, String type, double amount, Integer loanId, String description, Timestamp createdAt) {
            this.transactionId = transactionId;
            this.fromUserId = fromUserId;
            this.toUserId = toUserId;
            this.type = type;
            this.amount = amount;
            this.loanId = loanId;
            this.description = description;
            this.createdAt = createdAt;
        }
    }