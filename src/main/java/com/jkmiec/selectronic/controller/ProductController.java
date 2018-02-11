package com.jkmiec.selectronic.controller;

import com.jkmiec.selectronic.controller.exceptions.CategoryNotFound;
import com.jkmiec.selectronic.controller.exceptions.ProductNotFound;
import com.jkmiec.selectronic.entity.Product;
import com.jkmiec.selectronic.service.ICategoryService;
import com.jkmiec.selectronic.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private IProductService productService;

    @Autowired
    private ICategoryService categoryService;

    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getOneProductsById(@PathVariable Long productId) {
        return productService.getOneProductsById(productId)
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @GetMapping("category/{categoryId}/products/")
    public ResponseEntity<List<Product>> getAllProductsByCategoryId(@PathVariable Long categoryId) {
        return productService.getAllProductsByCategoryId(categoryId)
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @GetMapping("/category/{categoryId}/products/{productId}")
    public ResponseEntity<Product> getOneProductByIdAndCategoryId(@PathVariable Long productId, @PathVariable Long categoryId) {
        return productService.getProductByProductIdAndCategoryId(productId, categoryId)
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @PostMapping("/products")
    public ResponseEntity createProduct(@RequestBody Product product, UriComponentsBuilder b) throws CategoryNotFound {
        if (categoryService.checkIfExist(product.getCategory().getId())) {
            Long id = productService.createProduct(product);
            UriComponents uriComponents = b.path("/products/{productId}").buildAndExpand(id);
            return ResponseEntity.created(uriComponents.toUri()).build();
        } else {
            throw new CategoryNotFound(String.format("Category not found, Category id: %d", product.getCategory().getId()));
        }
    }

    @PutMapping("/products/{productId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "product updated")
    public void updateProduct(@RequestBody Product product, @PathVariable Long productId) throws ProductNotFound, CategoryNotFound {
        if (productService.checkIfExist(productId)) {
            productService.updateProduct(product, productId);
        } else {
            throw new ProductNotFound(String.format("Product not found, Product id: %d", productId));
        }
    }


}
