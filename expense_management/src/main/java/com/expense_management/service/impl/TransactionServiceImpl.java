package com.expense_management.service.impl;

import com.expense_management.dto.TransactionResponse;
import com.expense_management.entity.*;
import com.expense_management.repository.GroupRepository;
import com.expense_management.repository.GroupTransactionRepository;
import com.expense_management.repository.TransactionRepository;
import com.expense_management.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final GroupRepository groupRepository;
    private final GroupTransactionRepository groupTransactionRepository;

    @Override
    public TransactionResponse createTransaction(Transaction transaction, User user) {
        transaction.setUserId(user);
        transaction.setDate(new Date());
        transactionRepository.save(transaction);
        return TransactionResponse.builder()
                .id(transaction.getId())
                .type(transaction.getType())
                .description(transaction.getDescription())
                .amount(transaction.getAmount())
                .date(transaction.getDate())
                .categoryId(transaction.getCategoryId().getId())
                .build();
    }

    @Override
    public Transaction getTransactionById(Long id) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Transaction not found"));
        return Transaction.builder()
                .id(transaction.getId())
                .type(transaction.getType())
                .description(transaction.getDescription())
                .amount(transaction.getAmount())
                .date(transaction.getDate())
                .categoryId(transaction.getCategoryId())
                .build();
    }

    @Override
    public List<TransactionResponse> getTransactionsByUser(User user) {
        List<Transaction>transactions = transactionRepository.findByUserId(user);

        return transactions.stream()
                .map(transaction -> new TransactionResponse(
                        transaction.getId(),
                        transaction.getType(),
                        transaction.getDescription(),
                        transaction.getAmount(),
                        transaction.getDate(),
                        transaction.getCategoryId().getId()
                )).collect(Collectors.toList());
    }

    @Override
    public TransactionResponse updateTransaction(Long id, Transaction transaction) {
        Transaction existingTransaction = getTransactionById(id);
        existingTransaction.setType(transaction.getType() != null ? transaction.getType() : existingTransaction.getType());
        existingTransaction.setDescription(transaction.getDescription() != null ? transaction.getDescription() : existingTransaction.getDescription());
        existingTransaction.setAmount(transaction.getAmount() != 0 ? transaction.getAmount() : existingTransaction.getAmount());
        existingTransaction.setDate(transaction.getDate() != null ? transaction.getDate() : existingTransaction.getDate());
        existingTransaction.setCategoryId(transaction.getCategoryId() != null ? transaction.getCategoryId() : existingTransaction.getCategoryId());
        transactionRepository.save(existingTransaction);
        return TransactionResponse.builder()
                .id(existingTransaction.getId())
                .type(existingTransaction.getType())
                .description(existingTransaction.getDescription())
                .amount(existingTransaction.getAmount())
                .date(existingTransaction.getDate())
                .categoryId(existingTransaction.getCategoryId().getId())
                .build();
    }

    @Override
    public void deleteTransaction(Long id) {
        Transaction transaction = getTransactionById(id);
        transactionRepository.delete(transaction);
    }

    @Override
    public List<TransactionResponse> getAllTransactionsSorted(User userId, String sortBy, String timeframe) {
        Sort sort = getSortCriteria(sortBy, timeframe);
        List<Transaction>transactions = transactionRepository.findByUserId(userId, sort);
        return transactions.stream()
                .map(transaction -> new TransactionResponse(
                        transaction.getId(),
                        transaction.getType(),
                        transaction.getDescription(),
                        transaction.getAmount(),
                        transaction.getDate(),
                        transaction.getCategoryId().getId()
                )).collect(Collectors.toList());
    }

    private Sort getSortCriteria(String sortBy, String timeframe) {
        Sort sort = Sort.by(Sort.Direction.ASC, sortBy);

        if (timeframe.equals("day")) {
            sort = sort.and(Sort.by(Sort.Direction.ASC, "date"));
        } else if (timeframe.equals("week")) {
            sort = sort.and(Sort.by(Sort.Direction.ASC, "date"));
        } else if (timeframe.equals("month")) {
            sort = sort.and(Sort.by(Sort.Direction.ASC, "date"));
        }

        return sort;
    }

    @Override
    public GroupTransaction createGroupTransaction(Long groupId, Transaction transaction, User user) {
        transaction.setUserId(user);
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        Transaction savedTransaction = transactionRepository.save(transaction);

        GroupTransaction groupTransaction = GroupTransaction.builder()
                .groupId(group)
                .transactionId(savedTransaction)
                .build();

        return groupTransactionRepository.save(groupTransaction);
    }

    @Override
    public List<Transaction> getAllGroupTransactions(Group groupId) {
        List<GroupTransaction> groupTransactions = groupTransactionRepository.findByGroupId(groupId);
        return groupTransactions.stream()
                .map(GroupTransaction::getTransactionId)
                .collect(Collectors.toList());
    }


    @Override
    public List<TransactionResponse> getTransactionsByTime(User userId, String period) {
        Date startDate;
        Date endDate = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);

        switch (period.toLowerCase()) {
            case "day":
                calendar.add(Calendar.DATE, -1);
                break;
            case "week":
                calendar.add(Calendar.DATE, -7);
                break;
            case "month":
                calendar.add(Calendar.MONTH, -1);
                break;
            default:
                throw new IllegalArgumentException("Invalid period specified. Use 'day', 'week', or 'month'.");
        }

        startDate = calendar.getTime();
        List<Transaction> transactions = transactionRepository.findByUserIdAndDateBetween(userId, startDate, endDate);
        return transactions.stream()
                .map(transaction -> new TransactionResponse(
                        transaction.getId(),
                        transaction.getType(),
                        transaction.getDescription(),
                        transaction.getAmount(),
                        transaction.getDate(),
                        transaction.getCategoryId().getId()
                )).collect(Collectors.toList());
    }

    @Override
    public List<TransactionResponse> getTransactionsByCategory(User userId, Category categoryId) {
        List<Transaction> transactions = transactionRepository.findByUserIdAndCategoryId(userId, categoryId);
        return transactions.stream()
                .map(transaction -> new TransactionResponse(
                        transaction.getId(),
                        transaction.getType(),
                        transaction.getDescription(),
                        transaction.getAmount(),
                        transaction.getDate(),
                        transaction.getCategoryId().getId()
                )).collect(Collectors.toList());
    }

    @Override
    public Map<String, Object> getTransactionSummary(User userId) {
        List<Transaction> transactions = transactionRepository.findByUserId(userId);

        double totalSpent = 0;
        double totalReceived = 0;
        int totalTransactions = transactions.size();
        Map<String, Double> categorySummary = new HashMap<>();

        for (Transaction transaction : transactions) {
            double amount = transaction.getAmount();
            String categoryName = transaction.getCategoryId().getName();  // Assuming Category has a getName() method

            if ("expense".equalsIgnoreCase(transaction.getType())) {
                totalSpent += amount;
            } else if ("income".equalsIgnoreCase(transaction.getType())) {
                totalReceived += amount;
            }

            categorySummary.put(categoryName, categorySummary.getOrDefault(categoryName, 0.0) + amount);
        }

        Map<String, Object> summary = new HashMap<>();
        summary.put("totalExpense", totalSpent);
        summary.put("totalIncome", totalReceived);
        summary.put("totalTransactions", totalTransactions);
        summary.put("categorySummary", categorySummary);

        return summary;
    }


}
