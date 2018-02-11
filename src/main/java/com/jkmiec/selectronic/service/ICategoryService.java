package com.jkmiec.selectronic.service;

import com.jkmiec.selectronic.entity.Category;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {
    Optional<List<Category>> getAllCategories();

    Optional<Category> getCategoryById(Long categoryId);

    Long createCategory(Category category);

    boolean checkIfExist(Long categoryId);
}
