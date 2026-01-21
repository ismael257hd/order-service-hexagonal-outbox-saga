package com.ismael.order.application.usecase;


import com.ismael.order.application.exception.OrderNotFoundException;
import com.ismael.order.domain.model.Order;
import com.ismael.order.domain.port.OrderRepositoryPort;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompleteOrderUseCase {
	private static final Logger log = LoggerFactory.getLogger(CompleteOrderUseCase.class);
    private final OrderRepositoryPort repository;

    public CompleteOrderUseCase(OrderRepositoryPort repository) {
        this.repository = repository;
    }

    public void process(UUID orderId) {
        Order order = repository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        order.complete();
        repository.save(order);
        log.info("Order COMPLETED CompleteOrderUseCase [{}]", orderId);        
    }
}
