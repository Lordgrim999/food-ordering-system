package org.food.ordering.system.order.service.domain;

import lombok.extern.slf4j.Slf4j;
import org.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand;
import org.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse;
import org.food.ordering.system.order.service.domain.dto.track.TrackOrderQuery;
import org.food.ordering.system.order.service.domain.dto.track.TrackingOrderResponse;
import org.food.ordering.system.order.service.domain.ports.input.service.OrderApplicationService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@Slf4j
public class OrderApplicationServiceImpl implements OrderApplicationService {
    private final CreateOrderCommandHandler createOrderCommandHandler;
    private final TrackOrderCommandHandler trackOrderCommandHandler;

    public OrderApplicationServiceImpl(CreateOrderCommandHandler createOrderCommandHandler, TrackOrderCommandHandler trackOrderCommandHandler) {
        this.createOrderCommandHandler = createOrderCommandHandler;
        this.trackOrderCommandHandler = trackOrderCommandHandler;
    }

    @Override
    public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) {
        return null;
    }

    @Override
    public TrackingOrderResponse trackOrder(TrackOrderQuery trackOrderQuery) {
        return null;
    }
}
