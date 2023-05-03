package com.jaiveer.backend.product;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(Long id) {
        super("Could not find product " + id);
    }

    public ProductNotFoundException(String category) {
        super("Could not find products with category " + category);
    }
}
