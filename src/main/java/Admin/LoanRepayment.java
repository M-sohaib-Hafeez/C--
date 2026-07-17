package Admin;

import java.sql.Timestamp;

public class LoanRepayment {
        int repaymentId;
        int loanId;
        Timestamp loanGivenAt;
        double amount;
        Timestamp repaidAt;
        public LoanRepayment(int repaymentId, int loanId, Timestamp loanGivenAt, double amount, Timestamp repaidAt) {
            this.repaymentId = repaymentId;
            this.loanId = loanId;
            this.loanGivenAt = loanGivenAt;
            this.amount = amount;
            this.repaidAt = repaidAt;
        }
    }