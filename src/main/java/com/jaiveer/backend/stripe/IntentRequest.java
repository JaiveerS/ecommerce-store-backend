package com.jaiveer.backend.stripe;

import com.jaiveer.backend.order.OrderItems;
import lombok.*;

import java.util.List;

@Data
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class IntentRequest {
    private Long userId;
    private List<OrderItems> orderItems;

}
