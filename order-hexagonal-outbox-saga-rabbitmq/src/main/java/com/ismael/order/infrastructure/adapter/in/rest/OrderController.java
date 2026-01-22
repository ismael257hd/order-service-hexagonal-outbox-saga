package com.ismael.order.infrastructure.adapter.in.rest;

import com.ismael.order.application.usecase.CreateOrderUseCase;
import com.ismael.order.domain.model.OrderItem;
import com.ismael.order.infrastructure.adapter.in.rest.dto.CreateOrderItemRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;

    public OrderController(CreateOrderUseCase createOrderUseCase) {
        this.createOrderUseCase = createOrderUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UUID create(@RequestBody List<CreateOrderItemRequest> items) {

        List<OrderItem> orderItems = items.stream()
                .map(item -> new OrderItem(
                        item.productId(),
                        item.quantity(),
                        item.price()
                ))
                .toList();

        return createOrderUseCase.create(orderItems);
    }
}
