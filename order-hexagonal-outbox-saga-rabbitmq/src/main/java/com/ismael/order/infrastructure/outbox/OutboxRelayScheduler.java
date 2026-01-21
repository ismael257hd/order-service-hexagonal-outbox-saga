package com.ismael.order.infrastructure.outbox;

import com.ismael.order.application.port.out.EventPublisherPort;
import com.ismael.order.application.usecase.CompleteOrderUseCase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class OutboxRelayScheduler {
	private static final Logger log = LoggerFactory.getLogger(CompleteOrderUseCase.class);

    private final OutboxRepository repository;
    private final EventPublisherPort publisher;

    public OutboxRelayScheduler(
            OutboxRepository repository,
            EventPublisherPort publisher
    ) {
        this.repository = repository;
        this.publisher = publisher;
    }

    @Scheduled(fixedDelay = 5000)
    public void relay() {
        repository.findPendingEvents().forEach(event -> {
            try {
                publisher.publish(
                        event.getEventType(),
                        event.getPayload()
                );

                event.markProcessed();
                repository.save(event);
                
                log.info("Event {} published successfully", event.getId());

            } catch (Exception e) {                
            	log.error("Failed to publish event {} — will retry", event.getId(), e );
            }
        });
    }
}
