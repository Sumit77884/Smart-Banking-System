package com.banking.service;

import com.banking.model.Account;
import com.banking.model.User;
import com.banking.repository.AccountRepository;
import com.banking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

@Service
public class AccountService {

    @Autowired private AccountRepository accountRepository;
    @Autowired private UserRepository userRepository;

    public Account createAccount(String username, String accountType, BigDecimal initialDeposit) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Account account = Account.builder()
                .accountNumber(generateAccountNumber())
                .accountType(Account.AccountType.valueOf(accountType))
                .balance(initialDeposit != null ? initialDeposit : BigDecimal.ZERO)
                .status(Account.AccountStatus.ACTIVE)
                .user(user)
                .build();

        return accountRepository.save(account);
    }

    public List<Account> getUserAccounts(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return accountRepository.findByUserId(user.getId());
    }

    public Account getAccountById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    public Account getAccountByNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    private String generateAccountNumber() {
        Random random = new Random();
        String accountNumber;
        do {
            accountNumber = String.format("%010d", (long)(random.nextDouble() * 9_000_000_000L) + 1_000_000_000L);
        } while (accountRepository.existsByAccountNumber(accountNumber));
        return accountNumber;
    }
}
