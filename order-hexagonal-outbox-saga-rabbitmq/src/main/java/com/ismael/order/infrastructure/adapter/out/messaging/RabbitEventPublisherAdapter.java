package com.ismael.order.infrastructure.adapter.out.messaging;

import com.ismael.order.application.port.out.EventPublisherPort;
import com.ismael.order.application.usecase.CompleteOrderUseCase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitEventPublisherAdapter implements EventPublisherPort {
	private static final Logger log = LoggerFactory.getLogger(CompleteOrderUseCase.class);
	
    private final RabbitTemplate rabbitTemplate;

    public RabbitEventPublisherAdapter(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publish(String eventType, Object payload) {
        rabbitTemplate.convertAndSend("order.exchange", eventType, payload);
        log.info("Event published to RabbitMQ [{}]", eventType);
    }
}