package Admin;

import java.sql.Timestamp;

public class PoolContribution {
        int contributionId;
        int investorId;
        double amount;
        Timestamp contributedAt;
        public PoolContribution(int contributionId, int investorId, double amount, Timestamp contributedAt) {
            this.contributionId = contributionId;
            this.investorId = investorId;
            this.amount = amount;
            this.contributedAt = contributedAt;
        }
        @Override public String toString() { return contributionId + " - " + amount; }
    }