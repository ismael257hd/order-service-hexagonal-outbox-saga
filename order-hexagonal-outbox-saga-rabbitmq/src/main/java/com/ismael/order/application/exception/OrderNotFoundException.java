package com.ismael.order.application.exception;

import java.util.UUID;

public class OrderNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -880662121513435873L;

	public OrderNotFoundException(UUID orderId) {
        super("Order not found: " + orderId);
    }
}
