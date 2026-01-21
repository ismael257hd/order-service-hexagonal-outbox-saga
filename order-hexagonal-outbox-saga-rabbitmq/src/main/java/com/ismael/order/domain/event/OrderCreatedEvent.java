package com.ismael.order.domain.event;

import java.util.UUID;

public record OrderCreatedEvent(UUID orderId) {}
