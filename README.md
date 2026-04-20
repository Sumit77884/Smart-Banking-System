# 🏦 Smart Banking System

A full-stack banking application built with **Spring Boot + React + MySQL**.

---

## 🗂️ Project Structure

```
smart-banking-system/
├── backend/          ← Spring Boot (Java)
├── frontend/         ← React (JavaScript)
└── database/
    └── schema.sql    ← MySQL setup script
```

---

## ✅ Prerequisites (Install These First)

| Tool | Version | Download |
|------|---------|----------|
| Java JDK | 17+ | https://adoptium.net |
| Maven | 3.8+ | https://maven.apache.org/download.cgi |
| Node.js | 18+ | https://nodejs.org |
| MySQL Workbench | 8.0+ | https://dev.mysql.com/downloads/workbench |

---

## 🚀 STEP-BY-STEP SETUP

### STEP 1 — Install Java JDK 17

1. Go to https://adoptium.net
2. Download **JDK 17** (Temurin)
3. Run the installer, click Next → Next → Finish
4. Verify: open CMD, type `java -version`  → should show `17.x.x`

---

### STEP 2 — Install Maven

1. Go to https://maven.apache.org/download.cgi
2. Download **apache-maven-3.9.x-bin.zip**
3. Extract to `C:\maven`
4. Add to PATH:
   - Search "Environment Variables" in Windows
   - Under System Variables → Path → New → `C:\maven\bin`
5. Verify: open CMD, type `mvn -version`

---

### STEP 3 — Install Node.js

1. Go to https://nodejs.org
2. Download **LTS version** (18 or higher)
3. Run installer, click Next → Next → Finish
4. Verify: open CMD, type `node -v` and `npm -v`

---

### STEP 4 — Setup MySQL Database

1. Open **MySQL Workbench**
2. Connect to your local server (hostname: `localhost`, port: `3306`)
3. Click **File → Open SQL Script**
4. Navigate to `database/schema.sql` and open it
5. Press **Ctrl+Shift+Enter** (or click the lightning bolt ⚡) to run it
6. You should see: **"Database setup complete!"**

> **Important:** Remember your MySQL root password — you'll need it next!

---

### STEP 5 — Configure Database Password

Open this file in VS Code:
```
backend/src/main/resources/application.properties
```

Find this line and update YOUR password:
```properties
spring.datasource.password=root
```

If your MySQL password is `mypassword123`, change it to:
```properties
spring.datasource.password=mypassword123
```

---

### STEP 6 — Run the Backend (Spring Boot)

1. Open **VS Code**
2. Open the `backend/` folder: **File → Open Folder → select `backend`**
3. Open a terminal: **Terminal → New Terminal**
4. Type this command:

```bash
mvn spring-boot:run
```

5. Wait for: `Started SmartBankingApplication in X seconds`
6. Backend is running at: **http://localhost:8080**

> First run downloads dependencies (~5 min). Be patient!

---

### STEP 7 — Run the Frontend (React)

1. Open a **new terminal** (or open another VS Code window)
2. Navigate to the `frontend/` folder:

```bash
cd frontend
npm install
npm start
```

3. Wait — browser opens automatically at **http://localhost:3000**

---

## 🎉 You're Done! 

Open your browser: **http://localhost:3000**

### Default Admin Login:
- **Username:** `admin`
- **Password:** `admin123`

Or click **Register** to create a new account!

---

## 📱 Features

| Feature | Description |
|---------|-------------|
| 🔐 Register / Login | Secure JWT authentication |
| 💳 Open Accounts | Savings, Checking, Fixed Deposit |
| ⬇️ Deposit | Add money to any account |
| ⬆️ Withdraw | Withdraw from your account |
| ↔️ Transfer | Send money to any account by account number |
| 📋 Transaction History | View all past transactions with filters |
| 📊 Dashboard | Overview of balances and activity |

---

## 🔌 API Endpoints Reference

| Method | URL | Description |
|--------|-----|-------------|
| POST | `/api/auth/register` | Register new user |
| POST | `/api/auth/login` | Login, get JWT token |
| GET | `/api/accounts` | Get all user accounts |
| POST | `/api/accounts/create` | Create new account |
| POST | `/api/transactions/deposit` | Deposit money |
| POST | `/api/transactions/withdraw` | Withdraw money |
| POST | `/api/transactions/transfer` | Transfer money |
| GET | `/api/transactions/account/{id}` | Get transactions for account |

---

## ❗ Common Issues & Fixes

**Problem:** `mvn spring-boot:run` fails with DB connection error  
**Fix:** Check your MySQL password in `application.properties`

**Problem:** `npm install` fails  
**Fix:** Run `npm install --legacy-peer-deps`

**Problem:** Port 8080 already in use  
**Fix:** Change `server.port=8080` to `server.port=8081` in `application.properties`, and update `frontend/src/services/api.js` baseURL to port 8081

**Problem:** Java not found  
**Fix:** Restart VS Code/CMD after installing Java JDK

---

## 🛠️ Tech Stack

- **Backend:** Java 17, Spring Boot 3.2, Spring Security, JWT, JPA/Hibernate
- **Frontend:** React 18, React Router 6, Axios
- **Database:** MySQL 8
- **Build:** Maven (backend), npm (frontend)
