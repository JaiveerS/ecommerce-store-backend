package com.jaiveer.backend.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Product {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String productName;
    @Column(nullable = false)
    private double price;
    private String category;
    private String description;
    private String image;
}
