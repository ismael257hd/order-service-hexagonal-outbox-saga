package com.ismael.order.application.port.out;


public interface OutboxPort {

    void addEvent(
        String aggregateType,
        String aggregateId,
        String eventType,
        String payload
    );
}


