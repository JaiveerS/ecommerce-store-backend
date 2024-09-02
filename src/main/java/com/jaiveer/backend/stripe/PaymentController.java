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
    ResponseEntity<Map<String, String>> hostedCheckout(@RequestBody IntentRequest paymentIntentRequest) {
        return ResponseEntity.ok(stripeService.createPaymentIntent(paymentIntentRequest));
    }

    @PostMapping("/checkout/saveOrder")
    ResponseEntity<Order> saveOrder(@RequestBody String session, @RequestHeader("Authorization") String authorizationHeader) {
        System.out.println(session);
        String cleanedSessionId = session.replaceAll("=+$", "");
        System.out.println(cleanedSessionId);
        String jwt = authorizationHeader.substring(7);
        return ResponseEntity.ok(stripeService.fetchOrderInfoAndSaveOrder(cleanedSessionId, jwt));
    }


}
