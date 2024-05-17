package com.expense_management.repository;

import com.expense_management.entity.LoanDebt;
import com.expense_management.entity.Transaction;
import com.expense_management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanDebtRepository extends JpaRepository<LoanDebt, Long> {
    List<LoanDebt> findByUserId(User user);
}
