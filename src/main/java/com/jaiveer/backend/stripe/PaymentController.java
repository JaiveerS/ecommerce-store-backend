package com.jaiveer.backend.stripe;

import com.jaiveer.backend.order.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api")
public class PaymentController {
    private final StripeService stripeService;

    @PostMapping("/checkout/hosted")
    ResponseEntity<Map<String, String>> hostedCheckout(@RequestBody Order paymentIntentRequest) {
        return ResponseEntity.ok(stripeService.createPaymentIntent(paymentIntentRequest));
    }

}
