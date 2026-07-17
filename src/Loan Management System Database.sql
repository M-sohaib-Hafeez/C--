CREATE DATABASE IF NOT EXISTS Loan_Management_System;
USE Loan_Management_System;

CREATE TABLE Users(
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    user_role ENUM('Investor','Borrower','Admin') NOT NULL, 
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(15) NOT NULL,
    address TEXT NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    kyc_verified BOOLEAN DEFAULT FALSE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_user_email (email),
    INDEX idx_user_role (user_role)
);

CREATE TABLE Wallets(
    wallet_id INT PRIMARY KEY,
    balance DECIMAL(12,2) DEFAULT 0.00,
    FOREIGN KEY (wallet_id) REFERENCES Users(user_id) ON DELETE CASCADE,
    INDEX idx_wallet_balance (balance)
);

CREATE TABLE Loans(
    loan_id INT PRIMARY KEY AUTO_INCREMENT,
    borrower_id INT NOT NULL,
    loan_amount DECIMAL(12,2) NOT NULL,
    base_interest_rate DECIMAL(4,2) NOT NULL,
    tenure_months TINYINT NOT NULL,
    status ENUM('Pending', 'Active', 'repaid', 'Overdue', 'Rejected') DEFAULT 'Pending',
    due_date DATETIME,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (borrower_id) REFERENCES Users(user_id) ON DELETE CASCADE,
    INDEX idx_loans_borrower (borrower_id),
    INDEX idx_loans_status (status),
    INDEX idx_loans_due_date (due_date)
);

CREATE TABLE Pool_Contribution(
    contribution_id INT PRIMARY KEY AUTO_INCREMENT,
    investor_id INT NOT NULL,
    amount DECIMAL(12,2) NOT NULL,
    contributed_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (investor_id) REFERENCES Users(user_id) ON DELETE CASCADE,
    INDEX idx_contribution_investor (investor_id),
    INDEX idx_contribution_date (contributed_at)
);

CREATE TABLE Loan_Funding(
    funding_id INT PRIMARY KEY AUTO_INCREMENT,
    loan_id INT NOT NULL,
    contribution_id INT NOT NULL,
    amount_used DECIMAL(12,2) NOT NULL,
    allocated_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (loan_id) REFERENCES Loans(loan_id) ON DELETE CASCADE,
    FOREIGN KEY (contribution_id) REFERENCES Pool_Contribution(contribution_id) ON DELETE CASCADE,
    INDEX idx_funding_loan (loan_id),
    INDEX idx_funding_contribution (contribution_id)
);

CREATE TABLE Interest_Payout(
    payout_id INT PRIMARY KEY AUTO_INCREMENT,
    loan_id INT NOT NULL,
    investor_id INT NOT NULL,
    principal_share DECIMAL(12,2) NOT NULL,
    interest_earned DECIMAL(12,2) NOT NULL,
    platform_fee DECIMAL(12,2) NOT NULL,
    paid_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (loan_id) REFERENCES Loans(loan_id) ON DELETE CASCADE,
    FOREIGN KEY (investor_id) REFERENCES Users(user_id) ON DELETE CASCADE,
    INDEX idx_payout_loan (loan_id),
    INDEX idx_payout_investor (investor_id),
    INDEX idx_payout_date (paid_at)
);

CREATE TABLE Transactions(
    transaction_id INT PRIMARY KEY AUTO_INCREMENT,
    from_user_id INT,
    to_user_id INT,
type ENUM(
    'deposit',
    'loan_disbursement',
    'repayment',
    'interest_payout',
    'platform_fee',
    'transfer',
    'withdrawal'
),
    amount DECIMAL(12,2) NOT NULL,
    loan_id INT,
    description VARCHAR(250),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (from_user_id) REFERENCES Users(user_id) ON DELETE SET NULL,
    FOREIGN KEY (to_user_id) REFERENCES Users(user_id) ON DELETE SET NULL,
    FOREIGN KEY (loan_id) REFERENCES Loans(loan_id) ON DELETE SET NULL,
    INDEX idx_trans_from (from_user_id),
    INDEX idx_trans_to (to_user_id),
    INDEX idx_trans_type (type),
    INDEX idx_trans_loan (loan_id),
    INDEX idx_trans_date (created_at)
);

CREATE TABLE Loan_Repayment(
    repayment_id INT PRIMARY KEY AUTO_INCREMENT,
    loan_id INT NOT NULL,
    Loan_given_at DATETIME NOT NULL,
    amount DECIMAL(12,2) NOT NULL,
    repaid_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    is_early BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (loan_id) REFERENCES Loans(loan_id) ON DELETE CASCADE,
    INDEX idx_repayment_loan (loan_id),
    INDEX idx_repayment_date (repaid_at)
);


CREATE TABLE Audit_Log(
    log_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    action VARCHAR(200) NOT NULL,
    ip_address VARCHAR(50),
    timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE SET NULL,
    INDEX idx_audit_user (user_id),
    INDEX idx_audit_date (timestamp)
);

CREATE TABLE Settings(
    setting_key VARCHAR(50) PRIMARY KEY,
    setting_value VARCHAR(250) NOT NULL,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);


DROP VIEW IF EXISTS v_contribution_balance;
CREATE VIEW v_contribution_balance AS
SELECT 
    pc.contribution_id,
    pc.investor_id,
    pc.amount AS contributed_amount,
    COALESCE(SUM(lf.amount_used), 0) AS amount_allocated,
    (pc.amount - COALESCE(SUM(lf.amount_used), 0)) AS remaining_amount,
    pc.contributed_at
FROM Pool_Contribution pc
LEFT JOIN Loan_Funding lf 
    ON lf.contribution_id = pc.contribution_id
GROUP BY pc.contribution_id, pc.investor_id, pc.amount, pc.contributed_at;


DELIMITER //
CREATE TRIGGER set_loan_due_date 
BEFORE INSERT ON Loans
FOR EACH ROW
BEGIN
    SET NEW.due_date = DATE_ADD(NOW(), INTERVAL NEW.tenure_months MONTH);
END //
DELIMITER ;

DELIMITER //
CREATE TRIGGER create_user_wallet 
AFTER INSERT ON Users 
FOR EACH ROW
BEGIN
    INSERT INTO Wallets (wallet_id, balance) VALUES (NEW.user_id, 0.00);
END //
DELIMITER ;



INSERT INTO Settings (setting_key, setting_value) VALUES
('platform_fee_percentage', '25'),
('default_interest_rate', '4.0'),
('minimum_loan_amount', '1000'),
('maximum_loan_amount', '50000'),
('minimum_investment', '100'),
('currency', 'PKR');

-- Users table
CREATE INDEX idx_users_created ON Users(created_at);

-- Loans table
CREATE INDEX idx_loans_created ON Loans(created_at);
CREATE INDEX idx_loans_amount ON Loans(loan_amount);


-- Transactions table
CREATE INDEX idx_transactions_amount ON Transactions(amount);

-- Wallets table
CREATE INDEX idx_wallets_balance ON Wallets(balance DESC);


-- Pool_Contribution table
CREATE INDEX idx_pool_contrib_amount ON Pool_Contribution(amount);

insert into users(user_role, full_name, email, phone, address, password_hash) value
('Admin','Mubashir Awan','mubashirhafeez17@gmail.com','0311220834','Shah Faisal Colony Karachi','MyPasswordIsUnbreakeable');

select * from v_contribution_balance;