package com.expense_management.service;

import com.expense_management.dto.TransactionResponse;
import com.expense_management.entity.*;

import java.util.List;
import java.util.Map;

public interface TransactionService {
    TransactionResponse createTransaction(Transaction transaction, User user);
    Transaction getTransactionById(Long id);
    List<TransactionResponse> getTransactionsByUser(User user);
    TransactionResponse updateTransaction(Long id, Transaction transaction);
    void deleteTransaction(Long id);

    List<TransactionResponse> getAllTransactionsSorted(User userId, String sortBy, String timeframe);
    GroupTransaction createGroupTransaction(Long groupId, Transaction transaction, User user);
    List<Transaction> getAllGroupTransactions(Group groupId);
    List<TransactionResponse> getTransactionsByTime(User userId, String period);
    List<TransactionResponse> getTransactionsByCategory(User userId, Category categoryId);
    //Report
    Map<String, Object> getTransactionSummary(User userId);
}
