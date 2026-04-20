package com.banking.service;

import com.banking.model.Account;
import com.banking.model.Transaction;
import com.banking.repository.AccountRepository;
import com.banking.repository.TransactionRepository;
import com.banking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {

    @Autowired private TransactionRepository transactionRepository;
    @Autowired private AccountRepository accountRepository;
    @Autowired private UserRepository userRepository;

    @Transactional
    public Transaction deposit(Long accountId, BigDecimal amount, String description) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0)
            throw new RuntimeException("Amount must be positive");

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);

        return transactionRepository.save(Transaction.builder()
                .account(account)
                .type(Transaction.TransactionType.DEPOSIT)
                .amount(amount)
                .balanceAfter(account.getBalance())
                .description(description)
                .referenceNumber(UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .status(Transaction.TransactionStatus.SUCCESS)
                .build());
    }

    @Transactional
    public Transaction withdraw(Long accountId, BigDecimal amount, String description) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0)
            throw new RuntimeException("Amount must be positive");

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (account.getBalance().compareTo(amount) < 0)
            throw new RuntimeException("Insufficient balance");

        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);

        return transactionRepository.save(Transaction.builder()
                .account(account)
                .type(Transaction.TransactionType.WITHDRAWAL)
                .amount(amount)
                .balanceAfter(account.getBalance())
                .description(description)
                .referenceNumber(UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .status(Transaction.TransactionStatus.SUCCESS)
                .build());
    }

    @Transactional
    public Transaction transfer(Long fromAccountId, String toAccountNumber,
                                BigDecimal amount, String description) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0)
            throw new RuntimeException("Amount must be positive");

        Account fromAccount = accountRepository.findById(fromAccountId)
                .orElseThrow(() -> new RuntimeException("Source account not found"));

        Account toAccount = accountRepository.findByAccountNumber(toAccountNumber)
                .orElseThrow(() -> new RuntimeException("Destination account not found"));

        if (fromAccount.getBalance().compareTo(amount) < 0)
            throw new RuntimeException("Insufficient balance");

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        String ref = UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        transactionRepository.save(Transaction.builder()
                .account(toAccount)
                .type(Transaction.TransactionType.TRANSFER)
                .amount(amount)
                .balanceAfter(toAccount.getBalance())
                .description("Transfer from " + fromAccount.getAccountNumber())
                .referenceNumber(ref)
                .status(Transaction.TransactionStatus.SUCCESS)
                .build());

        return transactionRepository.save(Transaction.builder()
                .account(fromAccount)
                .type(Transaction.TransactionType.TRANSFER)
                .amount(amount.negate())
                .balanceAfter(fromAccount.getBalance())
                .description("Transfer to " + toAccountNumber +
                             (description != null ? " - " + description : ""))
                .referenceNumber(ref)
                .status(Transaction.TransactionStatus.SUCCESS)
                .build());
    }

    public List<Transaction> getAccountTransactions(Long accountId) {
        return transactionRepository.findByAccountIdOrderByCreatedAtDesc(accountId);
    }

    public List<Transaction> getUserTransactions(Long userId) {
        return transactionRepository.findByAccountUserIdOrderByCreatedAtDesc(userId);
    }
}
