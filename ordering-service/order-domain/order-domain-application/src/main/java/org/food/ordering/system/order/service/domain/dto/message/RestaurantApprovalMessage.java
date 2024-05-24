package org.food.ordering.system.order.service.domain.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.food.ordering.system.domain.valueObject.OrderApprovalStatus; 

import java.time.Instant;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class RestaurantApprovalMessage {
    private String id;
    private String orderId;
    private String sagaId;
    private String restaurantId;
    private OrderApprovalStatus orderApprovalStatus;
    private Instant createdAt;
    private List<String> failedMessages;
}
