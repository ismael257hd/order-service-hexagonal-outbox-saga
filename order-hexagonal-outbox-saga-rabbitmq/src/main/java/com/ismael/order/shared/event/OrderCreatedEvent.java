package com.ismael.order.shared.event;


import java.util.UUID;

public record OrderCreatedEvent(UUID orderId) {
}

