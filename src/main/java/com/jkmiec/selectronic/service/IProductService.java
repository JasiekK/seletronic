package com.jkmiec.selectronic.service;

import com.jkmiec.selectronic.controller.exceptions.CategoryNotFound;
import com.jkmiec.selectronic.entity.Product;

import java.util.List;
import java.util.Optional;

public interface IProductService {
    Optional<List<Product>> getAllProductsByCategoryId(Long categoryId);

    Optional<Product> getProductByProductIdAndCategoryId(Long productId, Long categoryId);

    Long createProduct(Product product);

    boolean checkIfExist(Long productId);

    void updateProduct(Product product, Long productId) throws CategoryNotFound;

    Optional<Product> getOneProductsById(Long productId);
}
