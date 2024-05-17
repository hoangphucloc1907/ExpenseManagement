package com.expense_management.service;

import com.expense_management.dto.UserRequest;
import com.expense_management.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAll();
    Optional<User> findByEmail(String email);

}
