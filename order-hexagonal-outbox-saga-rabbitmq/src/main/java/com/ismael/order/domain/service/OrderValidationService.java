package com.ismael.order.domain.service;

import com.ismael.order.domain.model.Order;

public class OrderValidationService {
    public void validate(Order order) {
        if (order.getTotal().signum() <= 0) {
            throw new IllegalArgumentException("Order total must be greater than zero");
        }
    }
}