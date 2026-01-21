package com.ismael.order.infrastructure.outbox;



import com.ismael.order.application.port.out.OutboxPort;
import org.springframework.stereotype.Component;

@Component
public class OutboxAdapter implements OutboxPort {

    private final OutboxRepository repository;

    public OutboxAdapter(OutboxRepository repository) {
        this.repository = repository;
    }

    @Override
    public void addEvent(
            String aggregateType,
            String aggregateId,
            String eventType,
            String payload
    ) {
        OutboxEventEntity event = new OutboxEventEntity(
                aggregateType,
                aggregateId,
                eventType,
                payload
        );

        repository.save(event);
    }
}


