package com.expense_management.service.impl;

import com.expense_management.dto.UserRequest;
import com.expense_management.entity.Token;
import com.expense_management.entity.TokenType;
import com.expense_management.entity.User;
import com.expense_management.repository.TokenRepository;
import com.expense_management.repository.UserRepository;
import com.expense_management.security.JwtService;
import com.expense_management.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {




}
