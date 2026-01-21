package com.ismael.order.application.port.in;

import com.ismael.order.domain.model.OrderItem;

import java.util.List;
import java.util.UUID;

public interface CreateOrderInputPort {
    UUID create(List<OrderItem> items);
}