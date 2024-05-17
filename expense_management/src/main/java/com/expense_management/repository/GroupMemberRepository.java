package com.expense_management.repository;

import com.expense_management.entity.Group;
import com.expense_management.entity.GroupMember;
import com.expense_management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {

}
