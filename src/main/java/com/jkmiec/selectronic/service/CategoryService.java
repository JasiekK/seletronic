package com.jkmiec.selectronic.service;

import com.jkmiec.selectronic.entity.Category;
import com.jkmiec.selectronic.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Optional<List<Category>> getAllCategories() {
        return Optional.of(categoryRepository.findAll());
    }

    @Override
    public Optional<Category> getCategoryById(Long categoryId) {
        return Optional.ofNullable(categoryRepository.findOne(categoryId));

    }

    @Override
    public Long createCategory(Category category) {
        return categoryRepository.save(category).getId();
    }

    @Override
    public boolean checkIfExist(Long categoryId) {
        return categoryRepository.exists(categoryId);
    }
}
