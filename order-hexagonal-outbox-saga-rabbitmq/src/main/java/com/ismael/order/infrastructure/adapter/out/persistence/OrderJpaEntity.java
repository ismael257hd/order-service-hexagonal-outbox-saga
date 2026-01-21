package com.ismael.order.infrastructure.adapter.out.persistence;

import com.ismael.order.domain.model.OrderStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class OrderJpaEntity {

    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItemJpaEntity> items = new ArrayList<>();

    protected OrderJpaEntity() {}

    public OrderJpaEntity(UUID id, OrderStatus status) {
        this.id = id;
        this.status = status;
    }

    public void addItem(String productId, int quantity, BigDecimal price) {
        OrderItemJpaEntity item = new OrderItemJpaEntity(productId, quantity, price, this);
        this.items.add(item);
    }

    public UUID getId() { return id; }
    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }
    public List<OrderItemJpaEntity> getItems() { return items; }
}