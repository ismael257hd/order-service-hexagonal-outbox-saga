package com.ismael.order.infrastructure.adapter.out.messaging;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ismael.order.application.saga.OrderSagaOrchestrator;
import com.ismael.order.application.usecase.CompleteOrderUseCase;

@Component
public class OrderCreatedListener {
	private static final Logger log = LoggerFactory.getLogger(CompleteOrderUseCase.class);

    private final OrderSagaOrchestrator saga;
    private final ObjectMapper objectMapper;

    public OrderCreatedListener(
            OrderSagaOrchestrator saga,
            ObjectMapper objectMapper
    ) {
        this.saga = saga;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "order.created.queue")
    public void onOrderCreated(String payload) throws Exception {
        UUID orderId = UUID.fromString(
            objectMapper.readTree(payload).get("orderId").asText()
        );
        log.info("Order CREATED onOrderCreated [{}]", orderId); 
        saga.execute(orderId);
    }
}
