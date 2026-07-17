package Admin;

import java.sql.Date;
import java.sql.Timestamp;

public class Loan {
        int loanId;
        int borrowerId;
        double loanAmount;
        double baseInterestRate;
        int tenureMonths;
        String status;
        Date dueDate;
        Timestamp createdAt;
        public Loan(int loanId, int borrowerId, double loanAmount, double baseInterestRate, int tenureMonths, String status, Date dueDate, Timestamp createdAt) {
            this.loanId = loanId;
            this.borrowerId = borrowerId;
            this.loanAmount = loanAmount;
            this.baseInterestRate = baseInterestRate;
            this.tenureMonths = tenureMonths;
            this.status = status;
            this.dueDate = dueDate;
            this.createdAt = createdAt;
        }
        @Override public String toString() { return loanId + " - " + loanAmount + " (" + status + ")"; }
    }