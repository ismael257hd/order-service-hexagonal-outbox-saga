package com.ismael.order.domain.port;

import com.ismael.order.domain.model.Order;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepositoryPort {
    Order save(Order order);
    Optional<Order> findById(UUID id);
}