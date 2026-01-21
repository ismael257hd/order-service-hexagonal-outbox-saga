package com.ismael.order.infrastructure.adapter.in.rest.dto;

import java.math.BigDecimal;

public record CreateOrderItemRequest(
	    String productId,
	    int quantity,
	    BigDecimal price
	) {}

