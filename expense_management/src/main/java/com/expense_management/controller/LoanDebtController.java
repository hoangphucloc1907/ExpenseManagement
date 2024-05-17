package com.expense_management.controller;

import com.expense_management.dto.LoandebtDTO;
import com.expense_management.entity.LoanDebt;
import com.expense_management.entity.User;
import com.expense_management.service.LoanDebtService;
import com.expense_management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/loandebts")
@RequiredArgsConstructor
public class LoanDebtController {
    private final LoanDebtService loanDebtService;
    private final UserService userService;

    @PostMapping("/loan")
    public ResponseEntity<LoandebtDTO> createLoan(
            @RequestBody LoanDebt loanDebt,
            @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByEmail(userDetails.getUsername()).orElseThrow(() -> new IllegalArgumentException("User not found"));
        loanDebt.setType("Loan");
        return ResponseEntity.ok(loanDebtService.createLoanDebt(loanDebt, user));
    }

    @PostMapping("/debt")
    public ResponseEntity<LoandebtDTO> createDebt(
            @RequestBody LoanDebt loanDebt,
            @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByEmail(userDetails.getUsername()).orElseThrow(() -> new IllegalArgumentException("User not found"));
        loanDebt.setType("Debt");
        return ResponseEntity.ok(loanDebtService.createLoanDebt(loanDebt, user));
    }


    @GetMapping("/{id}")
    public ResponseEntity<LoanDebt> getLoanDebtById(@PathVariable Long id) {
        LoanDebt loanDebt = loanDebtService.getLoanDebtById(id);
        return ResponseEntity.ok(loanDebt);
    }

    @GetMapping("/all")
    public ResponseEntity<List<LoandebtDTO>> getLoanDebtsByUser(
            @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByEmail(userDetails.getUsername()).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return ResponseEntity.ok(loanDebtService.getLoanDebtsByUser(user));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<LoandebtDTO> updateLoanDebt(@PathVariable Long id, @RequestBody LoanDebt updatedLoanDebt) {
        return ResponseEntity.ok(loanDebtService.updateLoanDebt(id, updatedLoanDebt));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteLoanDebt(@PathVariable Long id) {
        loanDebtService.deleteLoanDebt(id);
        return ResponseEntity.ok().build();
    }
}
