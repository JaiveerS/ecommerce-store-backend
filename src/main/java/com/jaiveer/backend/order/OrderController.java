package com.jaiveer.backend.order;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
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
        List<Order> Orders = orderRepo.findAll();
        return CollectionModel.of(Orders);
    }

    @GetMapping("/{id}")
    public List<Order> getOrdersById(@PathVariable Long id) {
        return orderRepo.findByUserId(id);
    }

    @CrossOrigin(exposedHeaders = {"Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"})
    @PostMapping("/order")
    public Order placeOrder(
            @RequestBody Order request
    ) throws Exception {
        return service.addOrder(request);
    }
}
