package com.ismael.order.domain.exception;

public class OrderException extends RuntimeException {
	
    private static final long serialVersionUID = 8123155375999496723L;

	public OrderException(String message) {
        super(message);
    }
}