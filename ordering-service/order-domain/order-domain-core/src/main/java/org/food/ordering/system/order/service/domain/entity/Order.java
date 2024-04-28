package org.food.ordering.system.order.service.domain.entity;

import org.food.ordering.system.domain.entity.AggregateRoot;
import org.food.ordering.system.domain.valueObject.*;
import org.food.ordering.system.order.service.domain.exception.OrderDomainException;
import org.food.ordering.system.order.service.domain.valueobject.OrderItemId;
import org.food.ordering.system.order.service.domain.valueobject.StreetAddress;
import org.food.ordering.system.order.service.domain.valueobject.TrackingId;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Order extends AggregateRoot<OrderId> {
    private final CustomerId customerId;
    private final RestaurantId restaurantId;
    private final Money price;
    private final StreetAddress deliveryAddress;
    private final List<OrderItem> items;
    private TrackingId trackingId;
    private OrderStatus orderStatus;
    private List<String> failureMessages;

    public void validateOrder()
    {
        validateInitialOrder();
        validateTotalPrice();
        validateItemsPrice();
    }

    private void validateItemsPrice() {
        Money totalItemsPrice=items.stream().map(orderItem->{
            validateOrderItemPrice(orderItem);
            return orderItem.getSubTotal();
        }).reduce(Money.ZERO,Money::addMoney);
        if(!price.equals(totalItemsPrice))
        {
            throw new OrderDomainException("Total Price: "+price.getAmount()+
                    " is not equal to the Order items total "+totalItemsPrice.getAmount());
        }
    }

    private void validateOrderItemPrice(OrderItem orderItem) {
        if(!orderItem.isValidPrice())
        {
            throw new OrderDomainException("Order item price "+orderItem.getPrice().getAmount()+
                    " is not valid for the product "+ orderItem.getProduct().getId().getValue());
        }
    }

    private void validateTotalPrice() {
        if(price==null || ! price.isMoneyGreaterThanZero())
        {
            throw new OrderDomainException("Total price must be grater than zero");
        }
    }

    private void validateInitialOrder() {
        if(orderStatus!=null || getOrderStatus()!=null)
            throw new OrderDomainException("Order is not in the correct state for initialization");
    }

    public void initializeOrder()
    {
        setId(new OrderId(UUID.randomUUID()));
        trackingId=new TrackingId(UUID.randomUUID());
        orderStatus=OrderStatus.PENDING;
        initializeOrderItems();
    }

    public void pay(){
        if(orderStatus!=OrderStatus.PENDING)
            throw new OrderDomainException("Order is not is the correct state for pay operation");
        orderStatus=OrderStatus.PAID;
    }
    public void approve(){
        if(orderStatus!=OrderStatus.PAID)
            throw new OrderDomainException("Order is not is the correct state for approve operation");
        orderStatus=OrderStatus.APPROVED;
    }

    public void initCancel(List<String> failureMessages){
        if(orderStatus!=OrderStatus.PAID)
            throw new OrderDomainException("Order is not is the correct state for initCancel operation");
        orderStatus=OrderStatus.CANCELLING;
        updateFailureMessages(failureMessages);
    }

    public void cancel(List<String> failureMessages){
        if(!(orderStatus==OrderStatus.PENDING || orderStatus==OrderStatus.CANCELLING))
            throw new OrderDomainException("Order is not is the correct state for cancel operation");
        orderStatus=OrderStatus.CANCELLED;
        updateFailureMessages(failureMessages);
    }

    private void updateFailureMessages(List<String> failureMessages) {
        if(this.failureMessages!=null && failureMessages!=null)
            this.failureMessages.addAll(failureMessages);
        if(this.failureMessages==null && failureMessages!=null)
        {
            this.failureMessages=new ArrayList<>(failureMessages);
        }
    }


    private void initializeOrderItems() {
        long orderItemId=1;
        for(OrderItem orderItem:items)
        {
            orderItem.initializeOrderItem(super.getId(),new OrderItemId(orderItemId++));
        }
    }

    private Order(Builder builder) {
        super.setId(builder.orderId);
        customerId = builder.customerId;
        restaurantId = builder.restaurantId;
        price = builder.price;
        deliveryAddress = builder.deliveryAddress;
        items = builder.items;
        trackingId = builder.trackingId;
        orderStatus = builder.orderStatus;
        failureMessages = builder.failureMessages;
    }


    public CustomerId getCustomerId() {
        return customerId;
    }

    public RestaurantId getRestaurantId() {
        return restaurantId;
    }

    public Money getPrice() {
        return price;
    }

    public StreetAddress getDeliveryAddress() {
        return deliveryAddress;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public TrackingId getTrackingId() {
        return trackingId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public List<String> getFailureMessages() {
        return failureMessages;
    }

    public static final class Builder {
        private OrderId orderId;
        private CustomerId customerId;
        private RestaurantId restaurantId;
        private Money price;
        private StreetAddress deliveryAddress;
        private List<OrderItem> items;
        private TrackingId trackingId;
        private OrderStatus orderStatus;
        private List<String> failureMessages;

        private Builder() {
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder orderId(OrderId val) {
            orderId = val;
            return this;
        }

        public Builder customerId(CustomerId val) {
            customerId = val;
            return this;
        }

        public Builder restaurantId(RestaurantId val) {
            restaurantId = val;
            return this;
        }

        public Builder price(Money val) {
            price = val;
            return this;
        }

        public Builder deliveryAddress(StreetAddress val) {
            deliveryAddress = val;
            return this;
        }

        public Builder items(List<OrderItem> val) {
            items = val;
            return this;
        }

        public Builder trackingId(TrackingId val) {
            trackingId = val;
            return this;
        }

        public Builder orderStatus(OrderStatus val) {
            orderStatus = val;
            return this;
        }

        public Builder failureMessages(List<String> val) {
            failureMessages = val;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}
