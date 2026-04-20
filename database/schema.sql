-- =============================================
--  SMART BANKING SYSTEM - DATABASE SETUP
--  Run this in MySQL Workbench FIRST
-- =============================================

-- Step 1: Create the database
CREATE DATABASE IF NOT EXISTS smart_banking_db;
USE smart_banking_db;

-- Step 2: Create users table
CREATE TABLE IF NOT EXISTS users (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    username   VARCHAR(50)  NOT NULL UNIQUE,
    email      VARCHAR(100) NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    full_name  VARCHAR(100),
    phone      VARCHAR(20),
    address    VARCHAR(255),
    role       ENUM('USER', 'ADMIN') DEFAULT 'USER',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- Step 3: Create accounts table
CREATE TABLE IF NOT EXISTS accounts (
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    account_number VARCHAR(20)  NOT NULL UNIQUE,
    account_type   ENUM('SAVINGS', 'CHECKING', 'FIXED_DEPOSIT') NOT NULL,
    balance        DECIMAL(15, 2) DEFAULT 0.00,
    status         ENUM('ACTIVE', 'INACTIVE', 'FROZEN') DEFAULT 'ACTIVE',
    user_id        BIGINT NOT NULL,
    created_at     DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Step 4: Create transactions table
CREATE TABLE IF NOT EXISTS transactions (
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    account_id       BIGINT NOT NULL,
    type             ENUM('DEPOSIT', 'WITHDRAWAL', 'TRANSFER', 'PAYMENT') NOT NULL,
    amount           DECIMAL(15, 2) NOT NULL,
    balance_after    DECIMAL(15, 2),
    description      VARCHAR(255),
    reference_number VARCHAR(50),
    status           ENUM('SUCCESS', 'FAILED', 'PENDING') DEFAULT 'SUCCESS',
    created_at       DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (account_id) REFERENCES accounts(id) ON DELETE CASCADE
);

-- Step 5: (Optional) Insert a test admin user
-- Password is: admin123  (BCrypt encoded)
INSERT INTO users (username, email, password, full_name, phone, role) VALUES
('admin', 'admin@smartbank.com',
 '$2a$10$8K1p/a0dR5a8L9p5E0sNaOGkpk7r6fLRg3vS9zVOcJzS0sK5F2OGm',
 'Admin User', '9876543210', 'ADMIN')
ON DUPLICATE KEY UPDATE username = username;

-- Done! Your schema is ready.
SELECT 'Database setup complete!' AS status;
