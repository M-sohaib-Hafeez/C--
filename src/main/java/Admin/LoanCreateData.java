package Admin;

class LoanCreateData {
            int borrowerId;
            double amount;
            double interestRate;
            int tenureMonths;

            public LoanCreateData(int borrowerId, double amount, double interestRate, int tenureMonths) {
                this.borrowerId = borrowerId;
                this.amount = amount;
                this.interestRate = interestRate;
                this.tenureMonths = tenureMonths;
            }
}