package org.food.ordering.system.order.service.domain;

import lombok.extern.slf4j.Slf4j;
import org.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand;
import org.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse;
import org.food.ordering.system.order.service.domain.entity.Customer;
import org.food.ordering.system.order.service.domain.entity.Order;
import org.food.ordering.system.order.service.domain.entity.Restaurant;
import org.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import org.food.ordering.system.order.service.domain.exception.OrderDomainException;
import org.food.ordering.system.order.service.domain.mapper.OrderDataMapper;
import org.food.ordering.system.order.service.domain.ports.output.repository.CustomerRepository;
import org.food.ordering.system.order.service.domain.ports.output.repository.OrderRepository;
import org.food.ordering.system.order.service.domain.ports.output.repository.RestaurantRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
public class CreateOrderCommandHandler {

    private final OrderDomainService orderDomainService;

    private final OrderRepository orderRepository;

    private final CustomerRepository customerRepository;

    private final RestaurantRepository restaurantRepository;

    private final OrderDataMapper orderDataMapper;

    public CreateOrderCommandHandler(OrderDomainService orderDomainService, OrderRepository orderRepository,
                                     CustomerRepository customerRepository, RestaurantRepository restaurantRepository,
                                     OrderDataMapper orderDataMapper) {
        this.orderDomainService = orderDomainService;
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.restaurantRepository = restaurantRepository;
        this.orderDataMapper = orderDataMapper;
    }


    @Transactional
    public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand)
    {
        checkCustomer(createOrderCommand.getCustomerId());
        Restaurant restaurant= checkRestaurant(createOrderCommand);
        Order order = orderDataMapper.createOrderCommandToOrder(createOrderCommand);
        OrderCreatedEvent orderCreatedEvent = orderDomainService.validateAndInitializeOrder(order, restaurant);
        order=saveOrder(order);

        return orderDataMapper.orderToCreateOrderResponse(order);
    }

    private Order saveOrder(Order order) {
        Order orderResult=orderRepository.saveOrder(order);
        if(orderResult==null)
        {
            log.error("Could not able to save order with id: {}", order.getId().getValue());
            throw new OrderDomainException("Could not able to save order with id: "+ order.getId().getValue());
        }
        log.info("Order was saved with the id: {}", order.getId().getValue());
        return orderResult;
    }

    private Restaurant checkRestaurant(CreateOrderCommand createOrderCommand) {
        Restaurant restaurant = orderDataMapper.createOrderCommandToRestaurant(createOrderCommand);
        Optional<Restaurant> restaurantInformation = restaurantRepository.findRestaurantInformation(restaurant);
        if(restaurantInformation.isEmpty())
        {
            log.warn("Could not find restaurant with restaurant id: {}",createOrderCommand.getRestaurantId());
            throw new OrderDomainException("Could not find restaurant with restaurant id: "+createOrderCommand.getRestaurantId());
        }
        return restaurantInformation.get();
    }

    private void checkCustomer(UUID customerId) {
        Optional<Customer> customer = customerRepository.findCustomer(customerId);
        if(customer.isEmpty()) {
            log.warn("Could not find the customer with id {}",customerId);
            throw new OrderDomainException("Could not find the customer with id: "+customerId);
        }
    }
}
