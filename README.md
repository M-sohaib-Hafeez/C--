# Student Loan Management System

A JavaFX + MySQL desktop application that models a peer-to-pool lending platform: investors contribute to a shared fund, borrowers apply for loans against that pool, and an admin panel handles approval, fund allocation, repayment tracking, and interest distribution.

## Features

**Borrower**
- Sign up / log in with hashed passwords
- Submit loan applications (amount, tenure)
- View active loans and repayment status
- Wallet-based deposits, withdrawals, and repayments

**Investor**
- Contribute funds to the lending pool
- Track investments and payout history
- Automatic, proportional interest distribution based on funding share

**Admin**
- Review and approve/reject loan applications
- Allocate pooled funds to approved loans
- Track platform fees and audit logs
- Manage system-wide settings (e.g. base interest rate)

**Core system**
- Wallet system with transaction history for every deposit, withdrawal, disbursement, repayment, and payout
- Audit logging on key user actions (registration, login, loan applications, transactions)
- Interest split between investors and a platform fee on every repayment

## Tech Stack

- **Language:** Java (JavaFX for UI, FXML views)
- **Database:** MySQL
- **Build tool:** Maven
- **Security:** Hashed passwords (`PasswordUtil`), externalized DB credentials

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   ├── Admin/                     # Admin-side logic (approvals, funding, settings, audit)
│   │   ├── com/example/mubashir/      # Core app: auth, wallet, loans, investments, UI controllers
│   │   └── module-info.java
│   └── resources/
│       ├── application-secrets.properties.example   # Template — copy this, fill in your own DB creds
│       └── com/example/mubashir/hello-view.fxml      # JavaFX view
└── Loan Management System Database.sql   # MySQL schema
```

## Getting Started

### Prerequisites
- JDK 17+
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
This project keeps DB credentials out of source control. Copy the example file and fill in your own values:
```bash
cp src/main/resources/application-secrets.properties.example src/main/resources/application-secrets.properties
```
Then edit `application-secrets.properties`:
```properties
db.url=jdbc:mysql://localhost:3306/loan_management_system
db.user=root
db.password=your_password_here
```
> This file is git-ignored — never commit your real credentials.

### 4. Run the app
Open the project in IntelliJ IDEA (or your IDE of choice) as a Maven project and run `Main.java`, or build via the wrapper:
```bash
./mvnw clean compile
```
(Adjust the run command to whatever goal your `pom.xml` is configured with, e.g. `javafx:run` if the JavaFX Maven plugin is set up.)

## Notes

- Passwords are hashed before storage — never stored in plaintext.
- Every wallet-affecting action (deposit, withdrawal, loan disbursement, repayment, interest payout, platform fee) is recorded in the `transactions` table for a full audit trail.

## License

Add a license here if you plan to make this public long-term (MIT is a common, permissive default for student projects).
