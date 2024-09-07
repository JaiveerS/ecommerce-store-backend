package com.jaiveer.backend.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> createRecommended(Long productId, int limit) {
        List<Object[]> purchasedWithList = productRepository.findMostPurchasedWithProduct(productId, limit);
        List<Product> recommended = new ArrayList<>();

        for (Object[] o : purchasedWithList) {
            Long ItemId = (Long) o[1];
            Product product = productRepository.findProductById(ItemId);
            recommended.add(product);
        }

        return recommended;
    }
}
