package com.expense_management.controller;

import com.expense_management.dto.TransactionResponse;
import com.expense_management.entity.Category;
import com.expense_management.entity.Transaction;
import com.expense_management.entity.User;
import com.expense_management.service.TransactionService;
import com.expense_management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;
    private final UserService userService;
    @PostMapping("/income")
    public ResponseEntity<TransactionResponse> createIncomeTransaction(
            @RequestBody Transaction transaction,
            @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByEmail(userDetails.getUsername()).orElseThrow(() -> new IllegalArgumentException("User not found"));
        transaction.setType("Income");
        return ResponseEntity.ok(transactionService.createTransaction(transaction, user));
    }

    @PostMapping("/expense")
    public ResponseEntity<TransactionResponse> createExpenseTransaction(
            @RequestBody Transaction transaction,
            @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByEmail(userDetails.getUsername()).orElseThrow(() -> new IllegalArgumentException("User not found"));
        transaction.setType("Expense");
        return ResponseEntity.ok(transactionService.createTransaction(transaction, user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.getTransactionById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<TransactionResponse>> getTransactionsByUser(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByEmail(userDetails.getUsername()).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return ResponseEntity.ok(transactionService.getTransactionsByUser(user));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<TransactionResponse> updateTransaction(@PathVariable Long id, @RequestBody Transaction transaction) {
        return ResponseEntity.ok(transactionService.updateTransaction(id, transaction));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/sortByTime")
    public ResponseEntity<List<TransactionResponse>> getTransactionsByTime(@AuthenticationPrincipal UserDetails userDetails, @RequestParam String period) {
        User user = userService.findByEmail(userDetails.getUsername()).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return ResponseEntity.ok(transactionService.getTransactionsByTime(user, period));
    }

    @GetMapping("/sortByCategory")
    public ResponseEntity<List<TransactionResponse>> getTransactionsByCategory(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam Category categoryId) {
        User user = userService.findByEmail(userDetails.getUsername()).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return ResponseEntity.ok(transactionService.getTransactionsByCategory(user, categoryId));
    }

    @GetMapping("/summary")
    public Map<String, Object> getTransactionSummary(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByEmail(userDetails.getUsername()).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return transactionService.getTransactionSummary(user);
    }

}
