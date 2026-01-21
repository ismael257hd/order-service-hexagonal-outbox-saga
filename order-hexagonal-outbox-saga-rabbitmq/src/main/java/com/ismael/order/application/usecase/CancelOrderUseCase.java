package com.ismael.order.application.usecase;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ismael.order.domain.port.OrderRepositoryPort;

public class CancelOrderUseCase {
	private static final Logger log = LoggerFactory.getLogger(CompleteOrderUseCase.class);
	
    private final OrderRepositoryPort repository;

    public CancelOrderUseCase(OrderRepositoryPort repository) {
        this.repository = repository;
    }

    public void cancel(UUID orderId) {
        repository.findById(orderId).ifPresent(order -> {
            order.cancel();
            log.info("ROLLBACK - CancelOrderUseCase: Status -> CANCELLED [{}]", orderId);
        });
    }
}