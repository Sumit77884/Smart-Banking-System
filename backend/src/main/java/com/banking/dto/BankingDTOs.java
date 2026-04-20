package com.banking.dto;

import java.math.BigDecimal;

// ---- Auth DTOs ----

class LoginRequest {
    private String username;
    private String password;

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public void setUsername(String v) { this.username = v; }
    public void setPassword(String v) { this.password = v; }
}

class RegisterRequest {
    private String username;
    private String email;
    private String password;
    private String fullName;
    private String phone;
    private String address;

    public String getUsername() { return username; }
    public String getEmail()    { return email; }
    public String getPassword() { return password; }
    public String getFullName() { return fullName; }
    public String getPhone()    { return phone; }
    public String getAddress()  { return address; }

    public void setUsername(String v) { this.username = v; }
    public void setEmail(String v)    { this.email = v; }
    public void setPassword(String v) { this.password = v; }
    public void setFullName(String v) { this.fullName = v; }
    public void setPhone(String v)    { this.phone = v; }
    public void setAddress(String v)  { this.address = v; }
}

class AuthResponse {
    private String token;
    private String username;
    private String email;
    private String role;

    public AuthResponse(String token, String username, String email, String role) {
        this.token = token;
        this.username = username;
        this.email = email;
        this.role = role;
    }

    public String getToken()    { return token; }
    public String getUsername() { return username; }
    public String getEmail()    { return email; }
    public String getRole()     { return role; }
}

// ---- Account DTOs ----

class CreateAccountRequest {
    private String accountType;
    private BigDecimal initialDeposit;

    public String getAccountType()        { return accountType; }
    public BigDecimal getInitialDeposit() { return initialDeposit; }
    public void setAccountType(String v)        { this.accountType = v; }
    public void setInitialDeposit(BigDecimal v) { this.initialDeposit = v; }
}

// ---- Transaction DTOs ----

class DepositRequest {
    private Long accountId;
    private BigDecimal amount;
    private String description;

    public Long getAccountId()      { return accountId; }
    public BigDecimal getAmount()   { return amount; }
    public String getDescription()  { return description; }
    public void setAccountId(Long v)      { this.accountId = v; }
    public void setAmount(BigDecimal v)   { this.amount = v; }
    public void setDescription(String v)  { this.description = v; }
}

class WithdrawRequest {
    private Long accountId;
    private BigDecimal amount;
    private String description;

    public Long getAccountId()      { return accountId; }
    public BigDecimal getAmount()   { return amount; }
    public String getDescription()  { return description; }
    public void setAccountId(Long v)      { this.accountId = v; }
    public void setAmount(BigDecimal v)   { this.amount = v; }
    public void setDescription(String v)  { this.description = v; }
}

class TransferRequest {
    private Long fromAccountId;
    private String toAccountNumber;
    private BigDecimal amount;
    private String description;

    public Long getFromAccountId()      { return fromAccountId; }
    public String getToAccountNumber()  { return toAccountNumber; }
    public BigDecimal getAmount()       { return amount; }
    public String getDescription()      { return description; }
    public void setFromAccountId(Long v)        { this.fromAccountId = v; }
    public void setToAccountNumber(String v)    { this.toAccountNumber = v; }
    public void setAmount(BigDecimal v)         { this.amount = v; }
    public void setDescription(String v)        { this.description = v; }
}
