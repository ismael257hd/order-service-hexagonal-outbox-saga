package com.ismael.order.application.saga;


import java.util.UUID;

import com.ismael.order.application.usecase.CancelOrderUseCase;
import com.ismael.order.application.usecase.CompleteOrderUseCase;
import com.ismael.order.application.usecase.ProcessOrderUseCase;
import com.ismael.order.application.usecase.ProcessPaymentUseCase;
import com.ismael.order.domain.model.Order;
import com.ismael.order.domain.model.OrderStatus;
import com.ismael.order.domain.port.OrderRepositoryPort;

public class OrderSagaOrchestrator {

    private final OrderRepositoryPort repository;
    private final ProcessOrderUseCase processOrder;
    private final ProcessPaymentUseCase processPayment;
    private final CompleteOrderUseCase completeOrder;
    private final CancelOrderUseCase cancelOrder;

    public OrderSagaOrchestrator(
            OrderRepositoryPort repository,
            ProcessOrderUseCase processOrder,
            ProcessPaymentUseCase processPayment,
            CompleteOrderUseCase completeOrder,
            CancelOrderUseCase cancelOrder
    ) {
        this.repository = repository;
        this.processOrder = processOrder;
        this.processPayment = processPayment;
        this.completeOrder = completeOrder;
        this.cancelOrder = cancelOrder;
    }

    public void execute(UUID orderId) {

        Order order = repository.findById(orderId)
                .orElseThrow(() -> new IllegalStateException("Order not found: " + orderId));

        if (order.getStatus() == OrderStatus.CANCELLED ||
            order.getStatus() == OrderStatus.COMPLETED) {
            return;
        }

        try {
            if (order.getStatus() == OrderStatus.PENDING) {
                processOrder.process(orderId);
                order = reload(orderId);
            }
            
            if (order.getStatus() == OrderStatus.PROCESSING) {
                processPayment.process(orderId);
                order = reload(orderId);
            }

            if (order.getStatus() == OrderStatus.PAID) {
                completeOrder.process(orderId);
            }

        } catch (Exception ex) {
            cancelOrder.cancel(orderId);
        }
    }

    private Order reload(UUID orderId) {
        return repository.findById(orderId)
                .orElseThrow(() -> new IllegalStateException("Order not found after step: " + orderId));
    }
}


