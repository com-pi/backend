package com.example.authserver.application;

import com.example.authserver.application.port.out.persistence.EventRecordCommand;
import com.example.authserver.domain.Event;
import com.example.authserver.domain.EventRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventRecordService {

    private final EventRecordCommand eventRecordCommand;

    @Transactional
    public <T> void recordEvent(Event<T> event) {
        eventRecordCommand.save(event);
    }

    @Transactional
    public void markAsPublished(Long id) {
        eventRecordCommand.markAsPublished(id);
    }

    @Transactional(readOnly = true)
    public <T> Optional<EventRecord<T>> findByTransactionId(String transactionId, Class<T> clazz) {
        return eventRecordCommand.findByTransactionId(transactionId, clazz);
    }

}
