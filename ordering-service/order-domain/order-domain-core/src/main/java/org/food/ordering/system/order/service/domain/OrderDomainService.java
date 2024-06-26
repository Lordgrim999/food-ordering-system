package org.food.ordering.system.order.service.domain;

import org.food.ordering.system.order.service.domain.entity.Order;
import org.food.ordering.system.order.service.domain.entity.Restaurant;
import org.food.ordering.system.order.service.domain.event.OrderCancelledEvent;
import org.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import org.food.ordering.system.order.service.domain.event.OrderPaidEvent;

import java.util.List;

public interface OrderDomainService {

   OrderCreatedEvent validateAndInitializeOrder(Order order, Restaurant restaurant);
   OrderPaidEvent payOrder(Order order);
   void approveOrder(Order order);

    void cancelOrder(Order order, List<String> failureMessages);
   OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages);
}
