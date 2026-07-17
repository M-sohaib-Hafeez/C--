# Student Loan Management System

A JavaFX + MySQL desktop application that models a peer-to-pool lending platform: investors contribute to a shared fund, borrowers apply for loans against that pool, and a separate admin dashboard handles approval, fund allocation, repayment tracking, and interest distribution.

The project ships as **two JavaFX applications** sharing one database:
- **User app** (`com.example.mubashir.Main`) — sign up/login, borrow, invest, wallet, transactions, payouts
- **Admin dashboard** (`Admin.AdminBlock`) — loan approvals, fund allocation, audit logs, system settings

## Features

**Borrower**
- Sign up / log in with hashed passwords (`PasswordUtil`)
- Submit loan applications
- View active loans and repayment status
- Wallet-based deposits, withdrawals, and repayments

**Investor**
- Contribute funds to the lending pool
- Track investments and payout history
- Interest distribution based on funding share (`PayoutSystem`)

**Admin** (`AdminBlock`)
- Review and approve/reject loan applications
- Allocate pooled funds to approved loans (`LoanFunding`, `PoolContribution`)
- Track repayments, contribution balances, and audit logs
- Manage system-wide settings

**Core system**
- Wallet system with a transaction record for every deposit, withdrawal, disbursement, repayment, and payout
- Audit logging on key actions
- DB credentials are externalized to a git-ignored properties file — never hardcoded

## Tech Stack

- **Language:** Java 25
- **UI:** JavaFX 21.0.6 (`javafx-controls`, `javafx-fxml`)
- **Database:** MySQL, via `mysql-connector-j` 9.1.0
- **Build tool:** Maven (`javafx-maven-plugin`), wrapper included (`mvnw` / `mvnw.cmd`)
- **Testing:** JUnit 5.12.1

## Project Structure

```
├── pom.xml
├── mvnw / mvnw.cmd
├── src/
│   ├── Loan Management System Database.sql   # MySQL schema
│   └── main/
│       ├── java/
│       │   ├── module-info.java
│       │   ├── Admin/                        # Admin dashboard app
│       │   │   ├── AdminBlock.java           # Admin entry point (main)
│       │   │   ├── DatabaseService.java       # Admin-side DB access layer
│       │   │   ├── Loan.java, LoanCreateData.java, LoanFunding.java, LoanRepayment.java
│       │   │   ├── User.java, WalletInfo.java, PoolContribution.java, ContributionBalance.java
│       │   │   ├── AllocationDetail.java, ApprovalResult.java, AuditLog.java
│       │   │   ├── Setting.java, TransactionRow.java, TransferData.java
│       │   └── com/example/mubashir/          # User-facing app
│       │       ├── Main.java                 # User app entry point (main)
│       │       ├── Server.java                # DB access layer for the user app
│       │       ├── LogIn.java, SignIn.java, PasswordUtil.java
│       │       ├── Borrow.java, Investment.java, InvestmentSummary.java, LoanSummary.java
│       │       ├── Wallet.java, Transaction.java, PayoutSystem.java
│       │       ├── BasicInfo.java, StyleHelper.java
│       └── resources/
│           ├── application-secrets.properties.example   # Template — copy & fill in, see below
│           └── com/example/mubashir/hello-view.fxml
```

## Getting Started

### Prerequisites
- JDK 25
- MySQL Server running locally (or update the URL for a remote instance)
- Maven (or use the included `mvnw` / `mvnw.cmd` wrapper)

### 1. Clone the repo
```bash
git clone https://github.com/M-sohaib-Hafeez/Student_Loan_Management_System.git
cd Student_Loan_Management_System
```

### 2. Set up the database
Create a MySQL database and import the provided schema:
```bash
mysql -u root -p < "src/Loan Management System Database.sql"
```

### 3. Configure your credentials
DB credentials are kept out of source control. Copy the example file and fill in your own values:
```bash
cp src/main/resources/application-secrets.properties.example src/main/resources/application-secrets.properties
```
Then edit `application-secrets.properties`:
```properties
db.url=jdbc:mysql://localhost:3306/loan_management_system
db.user=root
db.password=your_password_here
```
> This file is git-ignored — never commit your real credentials. Both `Server.java` (user app) and `AdminBlock.java` (admin app) read from this same file at startup.

### 4. Run the app
Open the project in IntelliJ IDEA as a Maven project, then run either entry point directly:
- **User app:** run `com.example.mubashir.Main`
- **Admin dashboard:** run `Admin.AdminBlock`

> Note: `mvn clean javafx:run` is currently configured in `pom.xml` to launch a `HelloApplication` class that doesn't exist in this project — update the plugin's `mainClass` to `com.example.mubashir.Main` (or `Admin.AdminBlock`) if you want that command to work, or just run from your IDE as above.

## Notes

- Passwords are hashed before storage — never stored in plaintext.
- Every wallet-affecting action (deposit, withdrawal, disbursement, repayment, payout) is recorded for a full audit trail.

## License

Add a license here if you plan to make this public long-term (MIT is a common, permissive default for student projects).
