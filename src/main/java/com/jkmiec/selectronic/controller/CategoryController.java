package com.jkmiec.selectronic.controller;

import com.jkmiec.selectronic.entity.Category;
import com.jkmiec.selectronic.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        return categoryService.getAllCategories()
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long categoryId) {
        return categoryService.getCategoryById(categoryId)
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }
   
    @PostMapping("/categories")
    public ResponseEntity createCategory(@RequestBody Category category, UriComponentsBuilder b) {
        Long id = categoryService.createCategory(category);
        UriComponents uriComponents = b.path("/categories/{categoryId}").buildAndExpand(id);
        return ResponseEntity.created(uriComponents.toUri()).build();
    }
}
