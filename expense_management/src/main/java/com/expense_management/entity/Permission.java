package com.expense_management.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {
    ADMIN("admin");

    @Getter
    private final String permission;
}
