package com.jaiveer.backend.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepo;

    public Order addOrder(Order request) {
        return orderRepo.save(request);
    }

}
