package com.ismael.order.infrastructure.outbox;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface OutboxRepository extends JpaRepository<OutboxEventEntity, UUID> {

    @Query(
        value = """
            SELECT *
            FROM outbox_event
            WHERE processed = false
            ORDER BY created_at
            LIMIT 100
        """,
        nativeQuery = true
    )
    List<OutboxEventEntity> findPendingEvents();
    
    List<OutboxEventEntity> findByAggregateId(String aggregateId);
    
}
