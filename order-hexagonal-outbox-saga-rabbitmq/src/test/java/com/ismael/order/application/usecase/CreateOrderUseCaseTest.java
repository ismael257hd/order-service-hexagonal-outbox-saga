
package com.ismael.order.application.usecase;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ismael.order.application.port.out.OutboxPort;
import com.ismael.order.domain.model.OrderItem;
import com.ismael.order.domain.port.OrderRepositoryPort;
import com.ismael.order.domain.service.OrderValidationService;

class CreateOrderUseCaseTest {

    @Test
    void shouldCreateOrderAndSaveOutboxEvent() throws Exception {
       
        OrderRepositoryPort repo = mock(OrderRepositoryPort.class);
        OutboxPort outbox = mock(OutboxPort.class);
        OrderValidationService validationService = mock(OrderValidationService.class);
        
        CreateOrderUseCase useCase = new CreateOrderUseCase(
                repo, outbox, validationService, new ObjectMapper()
        );

        List<OrderItem> items = List.of(
                new OrderItem("monitor-dell-27", 1, new BigDecimal("1500"))
        );

        UUID orderId = useCase.create(items);

        verify(validationService, times(1)).validate(any());
        verify(repo, times(1)).save(any());
        verify(outbox, times(1)).addEvent(
                eq("ORDER"), 
                eq(orderId.toString()), 
                eq("ORDER_CREATED_EVENT"),
                anyString()
        );
    }
}
