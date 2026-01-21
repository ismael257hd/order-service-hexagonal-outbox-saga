package com.ismael.order.application.dto;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public record OrderSavedEvent(
    UUID orderId,
    LocalDateTime createdAt
) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public OrderSavedEvent(UUID orderId) {
        this(orderId, LocalDateTime.now());
    }
}
