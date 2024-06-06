package com.example.authserver.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
@Builder
public class Event<T> extends ApplicationEvent {

    private final Object source;
    private final String transactionId;
    private final String entityType;
    private final String entityId;
    private final EventType eventType;
    private final T data;

    public Event(Object source, String transactionId, String entityType, String entityId, EventType eventType, T data) {
        super(source);
        this.source = source;
        this.transactionId = transactionId;
        this.entityType = entityType;
        this.entityId = entityId;
        this.eventType = eventType;
        this.data = data;
    }
}
