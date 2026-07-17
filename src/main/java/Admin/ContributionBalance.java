package Admin;

import java.sql.Timestamp;

public class ContributionBalance {
        int contributionId;
        int investorId;
        double contributedAmount;
        double amountAllocated;
        double remainingAmount;
        Timestamp contributedAt;
        public ContributionBalance(int contributionId, int investorId, double contributedAmount, double amountAllocated, double remainingAmount, Timestamp contributedAt) {
            this.contributionId = contributionId;
            this.investorId = investorId;
            this.contributedAmount = contributedAmount;
            this.amountAllocated = amountAllocated;
            this.remainingAmount = remainingAmount;
            this.contributedAt = contributedAt;
        }
    }