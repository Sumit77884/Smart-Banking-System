package com.banking.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String accountNumber;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Column(precision = 15, scale = 2)
    private BigDecimal balance = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    private AccountStatus status = AccountStatus.ACTIVE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> transactions;

    @PrePersist
    protected void onCreate() { createdAt = LocalDateTime.now(); }

    public enum AccountType { SAVINGS, CHECKING, FIXED_DEPOSIT }
    public enum AccountStatus { ACTIVE, INACTIVE, FROZEN }

    public Account() {}

    public Account(Long id, String accountNumber, AccountType accountType,
                   BigDecimal balance, AccountStatus status, User user,
                   LocalDateTime createdAt, List<Transaction> transactions) {
        this.id = id; this.accountNumber = accountNumber;
        this.accountType = accountType; this.balance = balance;
        this.status = status; this.user = user;
        this.createdAt = createdAt; this.transactions = transactions;
    }

    public Long getId()                     { return id; }
    public String getAccountNumber()        { return accountNumber; }
    public AccountType getAccountType()     { return accountType; }
    public BigDecimal getBalance()          { return balance; }
    public AccountStatus getStatus()        { return status; }
    public User getUser()                   { return user; }
    public LocalDateTime getCreatedAt()     { return createdAt; }
    public List<Transaction> getTransactions() { return transactions; }

    public void setId(Long v)                       { this.id = v; }
    public void setAccountNumber(String v)          { this.accountNumber = v; }
    public void setAccountType(AccountType v)       { this.accountType = v; }
    public void setBalance(BigDecimal v)            { this.balance = v; }
    public void setStatus(AccountStatus v)          { this.status = v; }
    public void setUser(User v)                     { this.user = v; }
    public void setCreatedAt(LocalDateTime v)       { this.createdAt = v; }
    public void setTransactions(List<Transaction> v){ this.transactions = v; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private String accountNumber;
        private AccountType accountType;
        private BigDecimal balance = BigDecimal.ZERO;
        private AccountStatus status = AccountStatus.ACTIVE;
        private User user;
        private LocalDateTime createdAt;
        private List<Transaction> transactions;

        public Builder id(Long v)                   { this.id = v; return this; }
        public Builder accountNumber(String v)      { this.accountNumber = v; return this; }
        public Builder accountType(AccountType v)   { this.accountType = v; return this; }
        public Builder balance(BigDecimal v)        { this.balance = v; return this; }
        public Builder status(AccountStatus v)      { this.status = v; return this; }
        public Builder user(User v)                 { this.user = v; return this; }
        public Builder createdAt(LocalDateTime v)   { this.createdAt = v; return this; }
        public Builder transactions(List<Transaction> v) { this.transactions = v; return this; }

        public Account build() {
            return new Account(id, accountNumber, accountType, balance,
                               status, user, createdAt, transactions);
        }
    }
}
