package com.jaiveer.backend.stripe;

import com.jaiveer.backend.order.Order;
import com.jaiveer.backend.order.OrderItems;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class StripeService {

    @Value("${stripe.privateKey}")
    private String STRIPE_PRIVATE_KEY;

    public Map<String, String> createPaymentIntent(Order order) {
        Stripe.apiKey = STRIPE_PRIVATE_KEY;

        SessionCreateParams.Builder paramsBuilder =
                SessionCreateParams.builder()
                        .setUiMode(SessionCreateParams.UiMode.EMBEDDED)
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setCurrency("USD")
                        .setRedirectOnCompletion(SessionCreateParams.RedirectOnCompletion.NEVER);

        for (OrderItems orderItem : order.getOrderItems()) {
            paramsBuilder.addLineItem(
                    SessionCreateParams.LineItem.builder()
                            .setQuantity((long) orderItem.getQuantity())
                            .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                    .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                            .setName(orderItem.getProductName())
                                            .build())
                                    .setCurrency("USD")
                                    .setUnitAmount((long) orderItem.getPrice() * 100)
                                    .build()
                            )
                            .build()
            );
        }

        Session session;
        try {
            session = Session.create(paramsBuilder.build());

        } catch (StripeException e) {
            throw new RuntimeException(e);
        }

        Map<String, String> map = new HashMap<>();
        map.put("clientSecret", session.getRawJsonObject().getAsJsonPrimitive("client_secret").getAsString());

        System.out.println(session.getAmountTotal());
        return map;
    }
}
