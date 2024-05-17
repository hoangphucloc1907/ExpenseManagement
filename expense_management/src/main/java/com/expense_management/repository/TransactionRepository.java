package com.expense_management.repository;

import com.expense_management.entity.Category;
import com.expense_management.entity.Transaction;
import com.expense_management.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserId(User user);

    List<Transaction> findByUserId(User userId, Sort sort);

    List<Transaction> findByUserIdAndDateBetween(User userId, Date startDate, Date endDate);
    List<Transaction> findByUserIdAndCategoryId(User userId, Category categoryId);

}
