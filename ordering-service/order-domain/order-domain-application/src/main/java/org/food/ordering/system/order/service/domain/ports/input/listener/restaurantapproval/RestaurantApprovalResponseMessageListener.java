package org.food.ordering.system.order.service.domain.ports.input.listener.restaurantapproval;

import org.food.ordering.system.order.service.domain.dto.message.RestaurantApprovalMessage;

public interface RestaurantApprovalResponseMessageListener {

    void orderApproved(RestaurantApprovalMessage restaurantApprovalMessage);
    void orderRejected(RestaurantApprovalMessage restaurantApprovalMessage);
}
