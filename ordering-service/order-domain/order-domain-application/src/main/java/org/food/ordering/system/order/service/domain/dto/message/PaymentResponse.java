package org.food.ordering.system.order.service.domain.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.food.ordering.system.domain.valueObject.PaymentStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class PaymentResponse {
    private String id;
    private String customerId;
    private String orderId;
    private String paymentId;
    private String sagaId;
    private BigDecimal price;
    private PaymentStatus paymentStatus;
    private Instant createdAt;
    private List<String> failedMessages;

}
