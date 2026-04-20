package com.banking.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @Column(precision = 15, scale = 2, nullable = false)
    private BigDecimal amount;

    @Column(precision = 15, scale = 2)
    private BigDecimal balanceAfter;

    private String description;
    private String referenceNumber;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status = TransactionStatus.SUCCESS;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() { createdAt = LocalDateTime.now(); }

    public enum TransactionType  { DEPOSIT, WITHDRAWAL, TRANSFER, PAYMENT }
    public enum TransactionStatus { SUCCESS, FAILED, PENDING }

    public Transaction() {}

    public Transaction(Long id, Account account, TransactionType type,
                       BigDecimal amount, BigDecimal balanceAfter, String description,
                       String referenceNumber, TransactionStatus status, LocalDateTime createdAt) {
        this.id = id; this.account = account; this.type = type;
        this.amount = amount; this.balanceAfter = balanceAfter;
        this.description = description; this.referenceNumber = referenceNumber;
        this.status = status; this.createdAt = createdAt;
    }

    public Long getId()                     { return id; }
    public Account getAccount()             { return account; }
    public TransactionType getType()        { return type; }
    public BigDecimal getAmount()           { return amount; }
    public BigDecimal getBalanceAfter()     { return balanceAfter; }
    public String getDescription()          { return description; }
    public String getReferenceNumber()      { return referenceNumber; }
    public TransactionStatus getStatus()    { return status; }
    public LocalDateTime getCreatedAt()     { return createdAt; }

    public void setId(Long v)                       { this.id = v; }
    public void setAccount(Account v)               { this.account = v; }
    public void setType(TransactionType v)          { this.type = v; }
    public void setAmount(BigDecimal v)             { this.amount = v; }
    public void setBalanceAfter(BigDecimal v)       { this.balanceAfter = v; }
    public void setDescription(String v)            { this.description = v; }
    public void setReferenceNumber(String v)        { this.referenceNumber = v; }
    public void setStatus(TransactionStatus v)      { this.status = v; }
    public void setCreatedAt(LocalDateTime v)       { this.createdAt = v; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private Account account;
        private TransactionType type;
        private BigDecimal amount;
        private BigDecimal balanceAfter;
        private String description;
        private String referenceNumber;
        private TransactionStatus status = TransactionStatus.SUCCESS;
        private LocalDateTime createdAt;

        public Builder id(Long v)                   { this.id = v; return this; }
        public Builder account(Account v)           { this.account = v; return this; }
        public Builder type(TransactionType v)      { this.type = v; return this; }
        public Builder amount(BigDecimal v)         { this.amount = v; return this; }
        public Builder balanceAfter(BigDecimal v)   { this.balanceAfter = v; return this; }
        public Builder description(String v)        { this.description = v; return this; }
        public Builder referenceNumber(String v)    { this.referenceNumber = v; return this; }
        public Builder status(TransactionStatus v)  { this.status = v; return this; }
        public Builder createdAt(LocalDateTime v)   { this.createdAt = v; return this; }

        public Transaction build() {
            return new Transaction(id, account, type, amount, balanceAfter,
                                   description, referenceNumber, status, createdAt);
        }
    }
}
