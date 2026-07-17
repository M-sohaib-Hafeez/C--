package Admin;

class TransferData {
            int fromUserId;
            int toUserId;
            double amount;
            String description;

            public TransferData(int fromUserId, int toUserId, double amount, String description) {
                this.fromUserId = fromUserId;
                this.toUserId = toUserId;
                this.amount = amount;
                this.description = description;
            }
        }