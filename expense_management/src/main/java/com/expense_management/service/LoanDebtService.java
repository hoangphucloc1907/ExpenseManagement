package com.expense_management.service;

import com.expense_management.dto.LoandebtDTO;
import com.expense_management.entity.LoanDebt;
import com.expense_management.entity.User;

import java.util.List;

public interface LoanDebtService {
    LoandebtDTO createLoanDebt(LoanDebt loanDebt, User user);
    LoanDebt getLoanDebtById(Long id);
    List<LoandebtDTO> getLoanDebtsByUser(User user);
    LoandebtDTO updateLoanDebt(Long id, LoanDebt LoanDebt);
    void deleteLoanDebt(Long id);

}
