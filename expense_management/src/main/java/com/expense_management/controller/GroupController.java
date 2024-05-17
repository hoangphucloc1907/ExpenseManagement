package com.expense_management.controller;

import com.expense_management.dto.GroupRequest;
import com.expense_management.dto.TransactionResponse;
import com.expense_management.entity.*;
import com.expense_management.service.GroupService;
import com.expense_management.service.TransactionService;
import com.expense_management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/groups")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;
    private final TransactionService transactionService;
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Group> createGroup(@RequestBody GroupRequest groupRequest, @AuthenticationPrincipal UserDetails userDetails) {
        String creatorEmail = userDetails.getUsername();
        Group group = groupService.createGroup(groupRequest.getName(), groupRequest.getDescription(), creatorEmail);
        return ResponseEntity.status(HttpStatus.CREATED).body(group);
    }

    @PostMapping("/{groupId}/invite")
    public ResponseEntity<GroupMember> inviteMember(@PathVariable Long groupId, @RequestParam String email) {
        GroupMember groupMember = groupService.inviteMember(groupId, email);
        return ResponseEntity.status(HttpStatus.OK).body(groupMember);
    }

    @PostMapping("/{groupId}/transactions")
    public ResponseEntity<GroupTransaction> createGroupTransaction(
            @PathVariable Long groupId,
            @RequestBody Transaction transaction,
            @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByEmail(userDetails.getUsername()).orElseThrow(() -> new IllegalArgumentException("User not found"));
        GroupTransaction groupTransaction = transactionService.createGroupTransaction(groupId, transaction, user);
        return ResponseEntity.ok(groupTransaction);
    }

    @GetMapping("/{groupId}/getAllTransactions")
    public ResponseEntity<List<Transaction>> getAllGroupTransactions(@PathVariable Group groupId) {
        List<Transaction> transactions = transactionService.getAllGroupTransactions(groupId);
        return ResponseEntity.ok(transactions);
    }


    @GetMapping("/all")
    public List<Group> getAllGroups() {
        return groupService.getAllGroups();
    }

    @GetMapping("/{id}")
    public Group getGroupById(@PathVariable Long id) {
        return groupService.getGroupById(id);
    }

    @PutMapping("update/{id}")
    public Group updateGroup(@PathVariable Long id, @RequestBody Group newGroup) {
        return groupService.updateGroup(id, newGroup);
    }

    @DeleteMapping("delete/{id}")
    public boolean deleteGroup(@PathVariable Long id) {
        return groupService.deleteGroup(id);
    }
    @GetMapping("/sorted")
    public ResponseEntity<List<TransactionResponse>> getAllTransactionsSorted(@AuthenticationPrincipal UserDetails userDetails,
                                                                              @RequestParam String sortBy,
                                                                              @RequestParam String timeframe) {
        User user = userService.findByEmail(userDetails.getUsername()).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return ResponseEntity.ok(transactionService.getAllTransactionsSorted(user, sortBy, timeframe));
    }
}
