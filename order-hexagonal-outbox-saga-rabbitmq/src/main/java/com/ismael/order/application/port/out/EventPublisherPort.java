package com.ismael.order.application.port.out;

public interface EventPublisherPort {
    void publish(String eventType, Object payload);
}