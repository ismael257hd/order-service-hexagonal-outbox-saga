package com.ismael.order.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;

import com.ismael.order.application.usecase.CreateOrderUseCase;
import com.ismael.order.domain.model.OrderItem;
import com.ismael.order.infrastructure.outbox.OutboxEventEntity;
import com.ismael.order.infrastructure.outbox.OutboxRepository;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class OrderIntegrationTest {

    @Autowired
    private CreateOrderUseCase createOrderUseCase;

    @Autowired
    private OutboxRepository outboxRepository;
   
    @DynamicPropertySource
    static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url",
                () -> "jdbc:postgresql://localhost:5432/orders");
        registry.add("spring.datasource.username", () -> "order_user");
        registry.add("spring.datasource.password", () -> "order_pass");

        registry.add("spring.rabbitmq.host", () -> "localhost");
        registry.add("spring.rabbitmq.port", () -> 5672);
    }

    @Test
    void shouldPersistOutbox() {
        List<OrderItem> items = List.of(
                new OrderItem("monitor-dell-27", 1, new BigDecimal("1500.00"))
        );

        UUID orderId = createOrderUseCase.create(items);

        List<OutboxEventEntity> events =
                outboxRepository.findByAggregateId(orderId.toString());

        assertThat(events).hasSize(1);

        OutboxEventEntity event = events.get(0);

        assertThat(event.getEventType()).isEqualTo("ORDER_CREATED_EVENT");
        assertThat(event.getPayload()).contains(orderId.toString());
        assertThat(event.isProcessed()).isFalse();
    }
}
