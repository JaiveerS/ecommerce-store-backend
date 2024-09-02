package com.jaiveer.backend.stripe;

import com.jaiveer.backend.config.JwtService;
import com.jaiveer.backend.order.Order;
import com.jaiveer.backend.order.OrderItems;
import com.jaiveer.backend.order.OrderRepository;
import com.jaiveer.backend.user.UserRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.LineItem;
import com.stripe.model.LineItemCollection;
import com.stripe.model.ShippingDetails;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.param.checkout.SessionListLineItemsParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class StripeService {

    @Value("${stripe.privateKey}")
    private String STRIPE_PRIVATE_KEY;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;


    public Map<String, String> createPaymentIntent(IntentRequest intentRequest) {
        Stripe.apiKey = STRIPE_PRIVATE_KEY;

        SessionCreateParams.Builder paramsBuilder =
                SessionCreateParams.builder()
                        .setUiMode(SessionCreateParams.UiMode.EMBEDDED)
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setCurrency("USD")
                        .setCustomerEmail(userRepository.getEmailById(intentRequest.getUserId()))
                        .setShippingAddressCollection(SessionCreateParams.ShippingAddressCollection.builder()
                                .addAllowedCountry(SessionCreateParams.ShippingAddressCollection.AllowedCountry.CA)
                                .addAllowedCountry(SessionCreateParams.ShippingAddressCollection.AllowedCountry.US)
                                .build())
                        .addShippingOption(SessionCreateParams.ShippingOption.builder()
                                .setShippingRateData(SessionCreateParams.ShippingOption.ShippingRateData.builder()
                                        .setFixedAmount(SessionCreateParams.ShippingOption.ShippingRateData.FixedAmount.builder()
                                                .setCurrency("USD")
                                                .setAmount((long) 999)
                                                .build())
                                        .setDisplayName("Standard Shipping")
                                        .setType(SessionCreateParams.ShippingOption.ShippingRateData.Type.FIXED_AMOUNT)
                                        .build())
                                .build())

                        .setSubmitType(SessionCreateParams.SubmitType.PAY)
                        .setRedirectOnCompletion(SessionCreateParams.RedirectOnCompletion.ALWAYS)
                        .setReturnUrl("http://localhost:3000/success?session_={CHECKOUT_SESSION_ID}");
//                        .setRedirectOnCompletion(SessionCreateParams.RedirectOnCompletion.NEVER);

        for (OrderItems orderItem : intentRequest.getOrderItems()) {
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

    public Order fetchOrderInfoAndSaveOrder(String sessionID, String jwt) {
        Stripe.apiKey = STRIPE_PRIVATE_KEY;

        try {
            Session session = Session.retrieve(sessionID);
            ShippingDetails shippingDetails = session.getShippingDetails();
            String email = jwtService.extractUsername(jwt);
            Long userId = userRepository.findByEmailIgnoreCase(email).getId();

            SessionListLineItemsParams params = SessionListLineItemsParams.builder().build();

            LineItemCollection lineItems = session.listLineItems(params);

            List<OrderItems> orderItems = new ArrayList<>();

            for (LineItem lineItem : lineItems.getData()) {
                OrderItems orderItem = OrderItems.builder()
//                        .productId(productRepository.findByProductName(lineItem.getDescription()))
                        .productName(lineItem.getDescription())
                        .price((double) lineItem.getPrice().getUnitAmount() / 100)
                        .quantity(Math.toIntExact(lineItem.getQuantity()))
                        .build();

                orderItems.add(orderItem);
            }


            Order order = Order.builder()
                    .orderNumber(session.getId())
                    .userId(userId)
                    .fullName(session.getShippingDetails().getName())
                    .address(shippingDetails.getAddress().getLine1())
                    .city(shippingDetails.getAddress().getCity())
                    .province(shippingDetails.getAddress().getState())
                    .postalCode(shippingDetails.getAddress().getPostalCode())
                    .orderItems(orderItems)
                    .build();

            order = orderRepository.save(order);
            System.out.println("saved order to db");
            return order;

        } catch (StripeException e) {
            throw new RuntimeException(e);
        }
    }
}
