package com.ismael.order.infrastructure.adapter.out.persistence;


import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_items")
public class OrderItemJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; 

    private String productId;
    private int quantity;
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private OrderJpaEntity order; 

    protected OrderItemJpaEntity() {}

    public OrderItemJpaEntity(String productId, int quantity, BigDecimal price, OrderJpaEntity order) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.order = order;
    }

    public Long getId() { return id; }
    public String getProductId() { return productId; }
    public int getQuantity() { return quantity; }
    public BigDecimal getPrice() { return price; }
    public OrderJpaEntity getOrder() { return order; }
}
