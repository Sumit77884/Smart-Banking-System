package com.banking.controller;

import com.banking.model.Account;
import com.banking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired private AccountService accountService;

    @GetMapping
    public ResponseEntity<List<Account>> getMyAccounts(Authentication auth) {
        return ResponseEntity.ok(accountService.getUserAccounts(auth.getName()));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createAccount(@RequestBody Map<String, Object> request, Authentication auth) {
        try {
            String accountType = (String) request.get("accountType");
            BigDecimal initialDeposit = request.get("initialDeposit") != null
                    ? new BigDecimal(request.get("initialDeposit").toString()) : BigDecimal.ZERO;
            Account account = accountService.createAccount(auth.getName(), accountType, initialDeposit);
            return ResponseEntity.ok(Map.of(
                "id", account.getId(),
                "accountNumber", account.getAccountNumber(),
                "accountType", account.getAccountType(),
                "balance", account.getBalance(),
                "status", account.getStatus()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAccount(@PathVariable Long id) {
        try {
            Account account = accountService.getAccountById(id);
            return ResponseEntity.ok(Map.of(
                "id", account.getId(),
                "accountNumber", account.getAccountNumber(),
                "accountType", account.getAccountType(),
                "balance", account.getBalance(),
                "status", account.getStatus(),
                "createdAt", account.getCreatedAt()
            ));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
