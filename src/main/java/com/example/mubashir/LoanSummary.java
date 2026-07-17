package com.example.mubashir;

import java.sql.Timestamp;

class LoanSummary {
    private int loanId;
    private int amount;
    private float interestRate;
    private int tenure;
    private String status;
    private Timestamp dueDate;

    public LoanSummary(int loanId, int amount, float interestRate, int tenure,
                       String status, Timestamp dueDate) {
        this.loanId = loanId;
        this.amount = amount;
        this.interestRate = interestRate;
        this.tenure = tenure;
        this.status = status;
        this.dueDate = dueDate;
    }

    public int getLoanId() {
        return loanId;
    }

    public int getAmount() {
        return amount;
    }

    public float getInterestRate() {
        return interestRate;
    }

    public int getTenure() {
        return tenure;
    }

    public String getStatus() {
        return status;
    }

    public Timestamp getDueDate() {
        return dueDate;
    }
}