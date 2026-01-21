
package com.ismael.order.domain;

import com.ismael.order.domain.model.Order;
import com.ismael.order.domain.model.OrderItem;
import com.ismael.order.domain.model.OrderStatus;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

	@Test
    void shouldStartWithPendingStatus() {
        Order order = new Order(
                UUID.randomUUID(),
                List.of(new OrderItem("monitor-dell-27", 1, new BigDecimal("1500"))),
                OrderStatus.PENDING
        );

        assertEquals(OrderStatus.PENDING, order.getStatus());
    }

    @Test
    void shouldMoveFromPendingToProcessing() {
        Order order = new Order(
                UUID.randomUUID(),
                List.of(new OrderItem("monitor-dell-27", 1, new BigDecimal("1500"))),
                OrderStatus.PENDING
        );

        order.process();

        assertEquals(OrderStatus.PROCESSING, order.getStatus());
    }

    @Test
    void shouldThrowExceptionWhenProcessingFromInvalidState() {
        Order order = new Order(
                UUID.randomUUID(),
                List.of(new OrderItem("monitor-dell-27", 1, new BigDecimal("1500"))),
                OrderStatus.COMPLETED
        );

        assertThrows(IllegalStateException.class, order::process);
    }

    @Test
    void shouldMoveFromProcessingToCompleted() {
        Order order = new Order(
                UUID.randomUUID(),
                List.of(new OrderItem("monitor-dell-27", 1, new BigDecimal("1500"))),
                OrderStatus.PAID
        );

        order.complete();

        assertEquals(OrderStatus.COMPLETED, order.getStatus());
    }

    @Test
    void shouldCancelOrderWhenNotCompleted() {
        Order order = new Order(
                UUID.randomUUID(),
                List.of(new OrderItem("monitor-dell-27", 1, new BigDecimal("1500"))),
                OrderStatus.PENDING
        );

        order.cancel();

        assertEquals(OrderStatus.CANCELLED, order.getStatus());
    }

    @Test
    void shouldNotCancelCompletedOrder() {
        Order order = new Order(
                UUID.randomUUID(),
                List.of(new OrderItem("monitor-dell-27", 1, new BigDecimal("1500"))),
                OrderStatus.COMPLETED
        );

        assertThrows(IllegalStateException.class, order::cancel);
    }
}
