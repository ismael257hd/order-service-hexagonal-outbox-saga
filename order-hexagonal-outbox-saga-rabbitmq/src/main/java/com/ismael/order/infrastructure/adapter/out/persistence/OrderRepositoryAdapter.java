package com.ismael.order.infrastructure.adapter.out.persistence;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.ismael.order.domain.model.Order;
import com.ismael.order.domain.port.OrderRepositoryPort;

@Component
public class OrderRepositoryAdapter implements OrderRepositoryPort {

    private final OrderJpaRepository repository;

    public OrderRepositoryAdapter(OrderJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Order save(Order order) {
        Optional<OrderJpaEntity> existingEntity = repository.findById(order.getId());
        
        OrderJpaEntity entityToSave;
        if (existingEntity.isPresent()) {
            entityToSave = existingEntity.get();
            entityToSave.setStatus(order.getStatus());
            
            if (order.getItems() != null) {
	            order.getItems().forEach(item -> 
	            entityToSave.addItem(item.getProductId(), item.getQuantity(), item.getPrice()));
            }
        } else {
            entityToSave = new OrderJpaEntity(order.getId(), order.getStatus());
            
            if (order.getItems() != null) {
	            order.getItems().forEach(item -> 
	            entityToSave.addItem(item.getProductId(), item.getQuantity(), item.getPrice()));
            }
        }

        repository.save(entityToSave);
        return order;
    }
    
    @Override
    public Optional<Order> findById(UUID id) {
        return repository.findById(id)
            .map(entity -> new Order(
                entity.getId(), 
                null, 
                entity.getStatus() 
            ));
    }
}