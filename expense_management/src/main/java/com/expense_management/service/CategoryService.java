package com.expense_management.service;

import com.expense_management.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> getAllCategories();
    Optional<Category> getCategoryById(Long id);
    Category createCategory(Category category);
    Category updateCategory(Long id, Category newCategory);
    boolean deleteCategory(Long id);
}
