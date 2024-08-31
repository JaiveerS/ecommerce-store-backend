package com.jaiveer.backend.order;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderRepository orderRepository;
    private final OrderService orderService;

    @GetMapping("/hello")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello from secured endpoint");
    }

    @GetMapping("/all")
    public ResponseEntity<CollectionModel<Order>> getAllOrders() {
        return ResponseEntity.ok(CollectionModel.of(orderRepository.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Order>> getOrdersById(@PathVariable Long id) {
        return ResponseEntity.ok(orderRepository.findByUserId(id));
    }

    @PostMapping("/order")
    public ResponseEntity<Object> placeOrder(
            @RequestBody Order request
    ) {
        try {
            Order order = orderService.addOrder(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(order);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", e.getMessage()));
        }
    }
}
