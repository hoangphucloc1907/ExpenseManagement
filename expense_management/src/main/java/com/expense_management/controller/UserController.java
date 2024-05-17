package com.expense_management.controller;

import com.expense_management.dto.UserRequest;
import com.expense_management.entity.User;
import com.expense_management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/allUser")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> user = userService.getAll();
        return ResponseEntity.ok(user);
    }
}
