package Admin;

import java.sql.Timestamp;

public class AuditLog {
        int logId;
        Integer userId;
        String action;
        String ipAddress;
        Timestamp timestamp;
        public AuditLog(int logId, Integer userId, String action, String ipAddress, Timestamp timestamp) {
            this.logId = logId;
            this.userId = userId;
            this.action = action;
            this.ipAddress = ipAddress;
            this.timestamp = timestamp;
        }
    }