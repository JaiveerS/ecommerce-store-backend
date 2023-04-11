package com.jaiveer.backend.order;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OrderItems {
    @Id
    @GeneratedValue
    private Long id;
    private Long productId;
    private String productName;
    private double price;
    private int quantity;
}
