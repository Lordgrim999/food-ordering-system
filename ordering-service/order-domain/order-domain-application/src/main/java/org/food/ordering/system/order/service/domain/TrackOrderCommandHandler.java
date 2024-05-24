package org.food.ordering.system.order.service.domain;

import lombok.extern.slf4j.Slf4j;
import org.food.ordering.system.order.service.domain.dto.track.TrackOrderQuery;
import org.food.ordering.system.order.service.domain.dto.track.TrackingOrderResponse;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TrackOrderCommandHandler {
    public TrackingOrderResponse trackOrder(TrackOrderQuery trackOrderQuery)
    {
        return null;
    }
}
