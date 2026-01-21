package com.ismael.order.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ismael.order.application.port.out.OutboxPort;
import com.ismael.order.application.saga.OrderSagaOrchestrator;
import com.ismael.order.application.usecase.CancelOrderUseCase;
import com.ismael.order.application.usecase.CompleteOrderUseCase;
import com.ismael.order.application.usecase.CreateOrderUseCase;
import com.ismael.order.application.usecase.ProcessOrderUseCase;
import com.ismael.order.application.usecase.ProcessPaymentUseCase;
import com.ismael.order.domain.port.OrderRepositoryPort;
import com.ismael.order.domain.service.OrderValidationService;

@Configuration
public class BeanConfig {

    @Bean
    public CreateOrderUseCase createOrderUseCase(
    		OrderRepositoryPort orderRepository,
            OutboxPort outboxPort,
            OrderValidationService validationService,
            ObjectMapper objectMapper) {
        return new CreateOrderUseCase(orderRepository, outboxPort, validationService, objectMapper);
    }

    @Bean
    public CancelOrderUseCase cancelOrderUseCase(OrderRepositoryPort repository) {
        return new CancelOrderUseCase(repository);
    }

    @Bean
    public ProcessOrderUseCase processOrderUseCase(OrderRepositoryPort repository) {
        return new ProcessOrderUseCase(repository);
    }

    @Bean
    public ProcessPaymentUseCase processPaymentUseCase(OrderRepositoryPort repository) {
        return new ProcessPaymentUseCase(repository);
    }
    
    @Bean
    public CompleteOrderUseCase completeOrderUseCase(OrderRepositoryPort repository) {
        return new CompleteOrderUseCase(repository);
    }
    
    @Bean
    public OrderSagaOrchestrator orderSagaOrchestrator(
    		OrderRepositoryPort repository,
            ProcessOrderUseCase processOrder,
            ProcessPaymentUseCase processPayment,
            CompleteOrderUseCase completeOrder,
            CancelOrderUseCase cancelOrder
    ) {
        return new OrderSagaOrchestrator(
        	repository,	
            processOrder,
            processPayment,
            completeOrder,
            cancelOrder
        );
    }

    @Bean
    public OrderValidationService orderDomainService() {
        return new OrderValidationService();
    }
}
