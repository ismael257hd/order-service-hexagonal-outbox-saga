
package com.ismael.order.infrastructure.outbox;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "outbox_event")
public class OutboxEventEntity {

    @Id
    @GeneratedValue
    private UUID id;

    private String aggregateType;
    private String aggregateId;
    private String eventType;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String payload;

    private boolean processed = false;

    @Version 
    private Long version;

    private LocalDateTime createdAt;

    protected OutboxEventEntity() {}

    public OutboxEventEntity(
            String aggregateType,
            String aggregateId,
            String eventType,
            String payload
    ) {
        this.aggregateType = aggregateType;
        this.aggregateId = aggregateId;
        this.eventType = eventType;
        this.payload = payload;
    }

    @PrePersist
    void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public String getEventType() {
        return eventType;
    }

    public String getPayload() {
        return payload;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void markProcessed() {
        this.processed = true;
    }
}


