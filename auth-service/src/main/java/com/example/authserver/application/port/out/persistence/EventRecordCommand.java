package com.example.authserver.application.port.out.persistence;

import com.example.authserver.domain.Event;
import com.example.authserver.domain.EventRecord;

import java.util.Optional;

public interface EventRecordCommand {

    <T> void save(Event<T> event);
    void markAsPublished(Long id);
    <T> Optional<EventRecord<T>> findByTransactionId(String transactionId, Class<T> clazz);
}
