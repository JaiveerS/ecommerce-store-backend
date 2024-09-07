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


    @Query("SELECT DISTINCT p.productName, p.id, p.category, p.image, p.description, p.price FROM Product p WHERE LOWER(p.productName) LIKE LOWER(CONCAT('%' ,:productName, '%'))")
    Page<Product> findByProductNameContaining(@Param("productName") String productName, Pageable pageable);

    Product findProductByProductName(String productName);
    
    @Query(
            value = """
                    WITH itemOrders AS (
                        SELECT order_order_number, order_items_id
                        FROM orders_order_items
                             JOIN order_items oi ON oi.id = orders_order_items.order_items_id
                        WHERE product_id = :id
                    ),
                    otherItems AS (
                        SELECT itemOrders.order_order_number, orders_order_items.order_items_id
                        FROM itemOrders
                             JOIN orders_order_items ON itemOrders.order_order_number = orders_order_items.order_order_number
                             AND itemOrders.order_items_id != orders_order_items.order_items_id
                    )
                    SELECT COUNT(DISTINCT otherItems.order_items_id) AS purchase_count, oi.product_id
                    FROM otherItems
                         JOIN order_items oi ON otherItems.order_items_id = oi.id
                    GROUP BY oi.product_id
                    ORDER BY purchase_count DESC
                    LIMIT :limit
                    """,
            nativeQuery = true
    )
    List<Object[]> findMostPurchasedWithProduct(@Param("id") Long id, @Param("limit") int limit);

    Product findProductById(Long itemId);


    //    @Query("SELECT DISTINCT a.productId FROM Product a WHERE a.productName = :productName")
//    Long findByProductName(@Param("productName") String productName);

}
