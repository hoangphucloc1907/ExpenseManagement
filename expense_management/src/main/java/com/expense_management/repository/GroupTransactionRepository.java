package com.expense_management.repository;

import com.expense_management.entity.Group;
import com.expense_management.entity.GroupTransaction;
import com.expense_management.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupTransactionRepository extends JpaRepository<GroupTransaction, Long> {
    List<GroupTransaction> findByGroupId(Group groupId);

}
