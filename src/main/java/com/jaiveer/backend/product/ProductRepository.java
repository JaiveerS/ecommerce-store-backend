package com.jaiveer.backend.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByCategory(String category);

    @Query("SELECT DISTINCT p.category FROM Product p")
    List<String> findUniqueCategories();


    @Query("SELECT DISTINCT p.productName, p.id, p.category, p.image, p.description, p.price FROM Product p WHERE p.productName LIKE CONCAT(:productName, '%')")
    Page<Product> findByProductNamesStartingWith(@Param("productName") String productName, Pageable pageable);

    //    @Query("SELECT DISTINCT a.productId FROM Product a WHERE a.productName = :productName")
//    Long findByProductName(@Param("productName") String productName);

}
