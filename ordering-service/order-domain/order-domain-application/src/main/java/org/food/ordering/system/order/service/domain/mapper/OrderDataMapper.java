package org.food.ordering.system.order.service.domain.mapper;

import org.food.ordering.system.domain.valueObject.*;
import org.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand;
import org.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse;
import org.food.ordering.system.order.service.domain.dto.create.OrderAddress;
import org.food.ordering.system.order.service.domain.entity.Order;
import org.food.ordering.system.order.service.domain.entity.OrderItem;
import org.food.ordering.system.order.service.domain.entity.Product;
import org.food.ordering.system.order.service.domain.entity.Restaurant;
import org.food.ordering.system.order.service.domain.valueobject.StreetAddress;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class OrderDataMapper {

    public Restaurant createOrderCommandToRestaurant(CreateOrderCommand createOrderCommand)
    {
        return Restaurant.Builder.newBuilder().
                restaurantId(new RestaurantId(createOrderCommand.getRestaurantId()))
                .products(createOrderCommand.getOrderItems().stream()
                        .map(items->new Product(new ProductId(items.getProductId()))).toList())
                .build();
    }

    public CreateOrderResponse orderToCreateOrderResponse(Order order)
    {
        return CreateOrderResponse.builder()
                .orderTrackingId(order.getTrackingId().getValue())
                .orderStatus(order.getOrderStatus())
                .build();
    }

    public Order createOrderCommandToOrder(CreateOrderCommand createOrderCommand)
    {
        return Order.Builder.newBuilder()
                .customerId(new CustomerId(createOrderCommand.getCustomerId()))
                .restaurantId(new RestaurantId(createOrderCommand.getRestaurantId()))
                .deliveryAddress(orderAddressToStreetAddress(createOrderCommand.getOrderAddress()))
                .price(new Money(createOrderCommand.getPrice()))
                .items(orderItemsToOrderItemsEntities(createOrderCommand.getOrderItems()))
                .build();
    }

    private List<OrderItem> orderItemsToOrderItemsEntities(List<org.food.ordering.system.order.service.domain.dto.create.OrderItem> orderItems) {
        return orderItems
                .stream()
                .map(orderItem ->
                    OrderItem.Builder.newBuilder()
                            .product(new Product(new ProductId(orderItem.getProductId())))
                            .price(new Money(orderItem.getPrice()))
                            .quantity(orderItem.getQuantity())
                            .subTotal(new Money(orderItem.getSubTotal()))
                            .build()
                ).toList();
    }

    private StreetAddress orderAddressToStreetAddress(OrderAddress orderAddress) {
        return new StreetAddress(
                UUID.randomUUID(),
                orderAddress.getPostalCode(),
                orderAddress.getStreet(),
                orderAddress.getCity()
        );
    }

}
