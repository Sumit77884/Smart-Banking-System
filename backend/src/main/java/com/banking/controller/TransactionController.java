package com.banking.controller;

import com.banking.model.Transaction;
import com.banking.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired private TransactionService transactionService;

    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestBody Map<String, Object> request) {
        try {
            Long accountId = Long.valueOf(request.get("accountId").toString());
            BigDecimal amount = new BigDecimal(request.get("amount").toString());
            String description = (String) request.getOrDefault("description", "Deposit");
            Transaction txn = transactionService.deposit(accountId, amount, description);
            return ResponseEntity.ok(buildTxnResponse(txn));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(@RequestBody Map<String, Object> request) {
        try {
            Long accountId = Long.valueOf(request.get("accountId").toString());
            BigDecimal amount = new BigDecimal(request.get("amount").toString());
            String description = (String) request.getOrDefault("description", "Withdrawal");
            Transaction txn = transactionService.withdraw(accountId, amount, description);
            return ResponseEntity.ok(buildTxnResponse(txn));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestBody Map<String, Object> request) {
        try {
            Long fromAccountId = Long.valueOf(request.get("fromAccountId").toString());
            String toAccountNumber = (String) request.get("toAccountNumber");
            BigDecimal amount = new BigDecimal(request.get("amount").toString());
            String description = (String) request.getOrDefault("description", "Transfer");
            Transaction txn = transactionService.transfer(fromAccountId, toAccountNumber, amount, description);
            return ResponseEntity.ok(buildTxnResponse(txn));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<Transaction>> getAccountTransactions(@PathVariable Long accountId) {
        return ResponseEntity.ok(transactionService.getAccountTransactions(accountId));
    }

    private Map<String, Object> buildTxnResponse(Transaction txn) {
        return Map.of(
            "id", txn.getId(),
            "type", txn.getType(),
            "amount", txn.getAmount(),
            "balanceAfter", txn.getBalanceAfter(),
            "description", txn.getDescription() != null ? txn.getDescription() : "",
            "referenceNumber", txn.getReferenceNumber(),
            "status", txn.getStatus(),
            "createdAt", txn.getCreatedAt().toString()
        );
    }
}
