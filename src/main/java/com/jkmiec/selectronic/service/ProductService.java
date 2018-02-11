package com.jkmiec.selectronic.service;

import com.jkmiec.selectronic.controller.exceptions.CategoryNotFound;
import com.jkmiec.selectronic.entity.Product;
import com.jkmiec.selectronic.repository.CategoryRepository;
import com.jkmiec.selectronic.repository.ProductRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryService;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    @Override
    public Optional<List<Product>> getAllProductsByCategoryId(Long categoryId) {
        return Optional.of(productRepository.findAllByCategoryId(categoryId));
    }

    @Override
    public Optional<Product> getProductByProductIdAndCategoryId(Long productId, Long categoryId) {
        return Optional.ofNullable(productRepository.findOneByIdAndCategoryId(productId, categoryId));
    }

    @Override
    public Long createProduct(Product product) {
        return productRepository.save(product).getId();
    }

    @Override
    public boolean checkIfExist(Long productId) {
        return productRepository.exists(productId);
    }

    @Override
    public void updateProduct(Product product, Long productId) throws CategoryNotFound {
        Product productFromDb = productRepository.findOne(productId);
        productFromDb.setParameters(product.getParameters());
        productFromDb.setState(product.getState());
        productFromDb.setComment(product.getComment());
        if (categoryService.exists(product.getCategory().getId())) {
            productFromDb.setCategory(product.getCategory());
        } else {
            throw new CategoryNotFound(String.format("Category not found, Category id: %d", product.getCategory().getId()));
        }
    }

    @Override
    public Optional<Product> getOneProductsById(Long productId) {
        return Optional.ofNullable(productRepository.findOne(productId));
    }
}
