package com.jaiveer.backend.order;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderRepository orderRepo;
    private final OrderService service;

    @GetMapping("/hello")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello from secured endpoint");
    }

    @GetMapping("/all")
    public CollectionModel<Order> getAllOrders() {
        return CollectionModel.of(orderRepo.findAll());
    }

    @GetMapping("/{id}")
    public List<Order> getOrdersById(@PathVariable Long id) {
        return orderRepo.findByUserId(id);
    }

    @PostMapping("/order")
    public Order placeOrder(
            @RequestBody Order request
    ) throws Exception {
        return service.addOrder(request);
    }
}
