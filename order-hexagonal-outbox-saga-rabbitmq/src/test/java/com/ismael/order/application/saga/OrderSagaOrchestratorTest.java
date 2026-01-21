
package com.ismael.order.application.saga;

import com.ismael.order.application.usecase.*;
import com.ismael.order.domain.model.Order;
import com.ismael.order.domain.model.OrderItem;
import com.ismael.order.domain.port.OrderRepositoryPort;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

class OrderSagaOrchestratorTest {

	@Test
	void shouldExecuteHappyPathSaga() {

	    OrderRepositoryPort repo = mock(OrderRepositoryPort.class);
	    ProcessOrderUseCase process = mock(ProcessOrderUseCase.class);
	    ProcessPaymentUseCase payment = mock(ProcessPaymentUseCase.class);
	    CompleteOrderUseCase complete = mock(CompleteOrderUseCase.class);
	    CancelOrderUseCase cancel = mock(CancelOrderUseCase.class);

	    Order order = new Order(
	            UUID.randomUUID(),
	            List.of(new OrderItem("monitor-dell-27", 1, new BigDecimal("1500")))
	    );
	   // order.pending(); // CREATED → PENDING

	    when(repo.findById(order.getId()))
	            .thenReturn(Optional.of(order));

	    doAnswer(invocation -> {
	        order.process(); // PENDING → PROCESSING
	        return null;
	    }).when(process).process(order.getId());

	    doAnswer(invocation -> {
	        order.pay(); // PROCESSING → PAID
	        return null;
	    }).when(payment).process(order.getId());

	    doAnswer(invocation -> {
	        order.complete(); // PAID → COMPLETED
	        return null;
	    }).when(complete).process(order.getId());

	    OrderSagaOrchestrator saga = new OrderSagaOrchestrator(
	            repo, process, payment, complete, cancel
	    );

	    saga.execute(order.getId());

	    verify(process).process(order.getId());
	    verify(payment).process(order.getId());
	    verify(complete).process(order.getId());
	    verify(cancel, never()).cancel(any());
	}

}
