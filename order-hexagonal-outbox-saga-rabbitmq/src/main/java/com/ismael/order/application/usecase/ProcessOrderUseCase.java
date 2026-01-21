package com.ismael.order.application.usecase;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ismael.order.domain.port.OrderRepositoryPort;


public class ProcessOrderUseCase {
	private static final Logger log = LoggerFactory.getLogger(CompleteOrderUseCase.class);
	
    private final OrderRepositoryPort repository;

    public ProcessOrderUseCase(OrderRepositoryPort repository) {
        this.repository = repository;
    }

    public void process(UUID orderId) {
        repository.findById(orderId).ifPresent(order -> {
            order.process(); 
            repository.save(order);
            log.info("Order PROCESS ProcessOrderUseCase [{}]", orderId);
        });
    }
}