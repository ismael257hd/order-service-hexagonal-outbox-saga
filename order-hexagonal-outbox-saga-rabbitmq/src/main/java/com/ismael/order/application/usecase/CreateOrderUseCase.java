package com.ismael.order.application.usecase;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ismael.order.application.port.in.CreateOrderInputPort;
import com.ismael.order.application.port.out.OutboxPort;
import com.ismael.order.domain.model.Order;
import com.ismael.order.domain.model.OrderItem;
import com.ismael.order.domain.port.OrderRepositoryPort;
import com.ismael.order.domain.service.OrderValidationService;
import com.ismael.order.shared.event.OrderCreatedEvent;

public class CreateOrderUseCase implements CreateOrderInputPort {
	private static final Logger log = LoggerFactory.getLogger(CompleteOrderUseCase.class);
	
    private final OrderRepositoryPort orderRepository;
    private final OutboxPort outboxPort;
    private final OrderValidationService validationService;
    private final ObjectMapper objectMapper;

    public CreateOrderUseCase(
            OrderRepositoryPort orderRepository,
            OutboxPort outboxPort,
            OrderValidationService validationService,
            ObjectMapper objectMapper
    ) {
        this.orderRepository = orderRepository;
        this.outboxPort = outboxPort;
        this.validationService = validationService;
        this.objectMapper = objectMapper;
    }

    @Transactional
    @Override
    public UUID create(List<OrderItem> items) {

        UUID orderId = UUID.randomUUID();
        Order order = new Order(orderId, items);
        
        validationService.validate(order);
               
        orderRepository.save(order);
        log.info("Order CREATED CreateOrderUseCase [{}]", orderId); 
        
        publishOrderCreatedEvent(orderId);

        return orderId;
    }

    private void publishOrderCreatedEvent(UUID orderId) {
        try {
            OrderCreatedEvent event = new OrderCreatedEvent(orderId);
            String payload = objectMapper.writeValueAsString(event);

            outboxPort.addEvent(
                    "ORDER",
                    orderId.toString(),
                    "ORDER_CREATED_EVENT",
                    payload
            );
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to serialize OrderCreatedEvent", e);
        }
    }
}


