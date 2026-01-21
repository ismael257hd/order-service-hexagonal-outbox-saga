package com.ismael.order.domain.exception;

import java.util.UUID;

public class OrderNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1121626988078284862L;

	public OrderNotFoundException(UUID orderId) {
        super("Order not found with id: " + orderId);
    }
}
