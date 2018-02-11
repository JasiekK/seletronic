package com.jkmiec.selectronic.repository;

import com.jkmiec.selectronic.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByCategoryId(Long categoryId);

    Product findOneByIdAndCategoryId(Long productId, Long categoryId);
}
