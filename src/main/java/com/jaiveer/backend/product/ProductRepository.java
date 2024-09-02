package com.jaiveer.backend.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByCategory(String category);

    @Query("SELECT DISTINCT a.category FROM Product a")
    List<String> findUniqueCategories();

//    @Query("SELECT DISTINCT a.productId FROM Product a WHERE a.productName = :productName")
//    Long findByProductName(@Param("productName") String productName);

}
