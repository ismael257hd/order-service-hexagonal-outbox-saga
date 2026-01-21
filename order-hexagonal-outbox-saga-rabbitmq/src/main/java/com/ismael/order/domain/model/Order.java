package com.ismael.order.domain.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class Order {
    private UUID id;
    private List<OrderItem> items;
    private OrderStatus status;
    
    public Order(UUID id) {
        this.id = id;
        this.status = OrderStatus.PENDING;
    }
    
    public Order(UUID id, List<OrderItem> items) {
        this.id = id;
        this.items = items;
        this.status = OrderStatus.PENDING;
    }
    
    public Order(UUID id, List<OrderItem> items, OrderStatus status) {
        this.id = id;
        this.items = items;
        this.status = status; 
    }
        
    public void process() {
        if (this.status != OrderStatus.PENDING) {
            throw new IllegalStateException("Order must be PENDING to be PROCESSING");
        }
        this.status = OrderStatus.PROCESSING;
    }

    public void pay() {
        if (status != OrderStatus.PROCESSING) {
            throw new IllegalStateException("Order must be PROCESSING to be PAID");
        }
        this.status = OrderStatus.PAID;
    }

    public void complete() {
        if (status != OrderStatus.PAID) {
            throw new IllegalStateException("Order must be PAID to be COMPLETED");
        }
        this.status = OrderStatus.COMPLETED;
    }
    
    public void cancel() {
        if (this.status == OrderStatus.COMPLETED) {
            throw new IllegalStateException("Completed order cannot be cancelled");
        }
        this.status = OrderStatus.CANCELLED;
    }

    public BigDecimal getTotal() {
        return items.stream()
                .map(OrderItem::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public UUID getId() {
        return id;
    }

    public OrderStatus getStatus() {
        return status;
    }

	public List<OrderItem> getItems() {
		return items;
	}

	public void setItems(List<OrderItem> items) {
		this.items = items;
	}
}