package com.example.authserver.adapter.out.command;

import com.example.authserver.domain.Event;
import com.example.authserver.domain.EventRecord;
import com.example.authserver.domain.EventStatus;
import com.example.authserver.domain.EventType;
import com.example.common.baseentity.BaseTimeAbstractEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
public class EventRecordEntity extends BaseTimeAbstractEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String transactionId;
    @Enumerated(EnumType.STRING)
    private EventType eventType;
    private String entityType;
    private String entityId;
    @Lob
    private String jsonData;
    @Enumerated(EnumType.STRING)
    private EventStatus status;

    public static <T> EventRecordEntity fromEvent(Event<T> event, ObjectMapper objectMapper) throws JsonProcessingException {
        return EventRecordEntity.builder()
                .transactionId(event.getTransactionId())
                .eventType(event.getEventType())
                .entityType(event.getEntityType())
                .entityId(event.getEntityId())
                .jsonData(objectMapper.writeValueAsString(event.getData()))
                .build();
    }

    public static <T> EventRecord<T> toDomain(EventRecordEntity eventRecordEntity, Class<T> clazz, ObjectMapper objectMapper) throws JsonProcessingException {
        return EventRecord.<T>builder()
                .id(eventRecordEntity.id)
                .transactionId(eventRecordEntity.transactionId)
                .eventType(eventRecordEntity.eventType)
                .entityType(eventRecordEntity.entityType)
                .entityId(eventRecordEntity.entityId)
                .data(objectMapper.readValue(eventRecordEntity.jsonData, clazz))
                .status(eventRecordEntity.status)
                .createdAt(eventRecordEntity.getCreatedAt())
                .updatedAt(eventRecordEntity.getUpdatedAt())
                .build();
    }

    public void setPublishedTrue() {
        status = EventStatus.PUBLISHED;
    }

}
