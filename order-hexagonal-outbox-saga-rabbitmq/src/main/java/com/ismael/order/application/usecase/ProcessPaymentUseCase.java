
package com.ismael.order.application.usecase;

import com.ismael.order.domain.model.Order;
import com.ismael.order.domain.model.OrderStatus;
import com.ismael.order.domain.port.OrderRepositoryPort;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessPaymentUseCase {
	private static final Logger log = LoggerFactory.getLogger(CompleteOrderUseCase.class);
	
    private final OrderRepositoryPort repository;

    public ProcessPaymentUseCase(OrderRepositoryPort repository) {
        this.repository = repository;
    }

    public void process(UUID orderId) {
        repository.findById(orderId).ifPresentOrElse(order -> {
            if (order.getStatus() != OrderStatus.PROCESSING) {
                throw new IllegalStateException("Order must be PROCESSING to process payment: " + orderId);
            }

            boolean paymentSuccess = simulatePayment(order);

            if (paymentSuccess) {
                order.pay();
                repository.save(order);
                log.info("Order PAY ProcessPaymentUseCase [{}]", orderId);
            } else {
                order.cancel();
                repository.save(order);
                log.info("Order CANCEL ProcessPaymentUseCase [{}]", orderId);
                throw new RuntimeException("Payment failed for the order:" + orderId);
            }

        }, () -> {
            throw new IllegalStateException("Order not found: " + orderId);
        });
    }

    private boolean simulatePayment(Order order) {        
        return true; 
    }
}
