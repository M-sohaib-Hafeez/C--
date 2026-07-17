package com.example.mubashir;

import java.sql.Timestamp;

class InvestmentSummary {
    private int payoutId;
    private int loanId;
    private float principalShare;
    private float interestEarned;
    private float platformFee;
    private Timestamp paidAt;

    public InvestmentSummary(int payoutId, int loanId, float principalShare,
                             float interestEarned, float platformFee, Timestamp paidAt) {
        this.payoutId = payoutId;
        this.loanId = loanId;
        this.principalShare = principalShare;
        this.interestEarned = interestEarned;
        this.platformFee = platformFee;
        this.paidAt = paidAt;
    }

    public int getPayoutId() { return payoutId; }
    public int getLoanId() { return loanId; }
    public float getPrincipalShare() { return principalShare; }
    public float getInterestEarned() { return interestEarned; }
    public float getPlatformFee() { return platformFee; }
    public Timestamp getPaidAt() { return paidAt; }
}