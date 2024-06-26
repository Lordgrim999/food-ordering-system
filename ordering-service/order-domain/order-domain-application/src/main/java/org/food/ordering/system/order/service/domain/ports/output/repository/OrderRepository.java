package org.food.ordering.system.order.service.domain.ports.output.repository;

import org.food.ordering.system.order.service.domain.entity.Order;
import org.food.ordering.system.order.service.domain.valueobject.TrackingId;

import java.util.Optional;

public interface OrderRepository {

    Order saveOrder(Order order);

    Optional<Order> findByTrackingId(TrackingId trackingId);
}
