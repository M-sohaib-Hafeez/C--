package Admin;

import java.sql.Timestamp;

public class User {
        int userId;
        String userRole;
        String fullName;
        String email;
        String phone;
        String address;
        boolean kycVerified;
        Timestamp createdAt;
        public User(int userId, String userRole, String fullName, String email, String phone, String address, boolean kycVerified, Timestamp createdAt) {
            this.userId = userId;
            this.userRole = userRole;
            this.fullName = fullName;
            this.email = email;
            this.phone = phone;
            this.address = address;
            this.kycVerified = kycVerified;
            this.createdAt = createdAt;
        }
        @Override public String toString() { return userId + " - " + fullName; }
    }