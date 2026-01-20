package com.blognews.service;

import com.blognews.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();
    Category findById(Long id);
    Category save(Category category);
    void delete(Long id);
    long countAll();
}
