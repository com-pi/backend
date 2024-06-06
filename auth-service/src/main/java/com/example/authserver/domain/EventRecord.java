package com.example.authserver.domain;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record EventRecord<T>(
        Long id,
        String transactionId,
        String entityType,
        String entityId,
        EventType eventType,
        T data,
        EventStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt){
}
