package com.example.authserver.adapter.out.command;

import com.example.authserver.adapter.out.entity.EventRecordEntity;
import com.example.authserver.adapter.out.repository.EventRecordJpaRepository;
import com.example.authserver.application.port.out.persistence.EventRecordCommand;
import com.example.authserver.domain.Event;
import com.example.authserver.domain.EventRecord;
import com.example.common.exception.InternalServerException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EventRecordCommandImpl implements EventRecordCommand {

    private final EventRecordJpaRepository eventRecordJpaRepository;
    private final ObjectMapper objectMapper;

    @Override
    public <T> void save(Event<T> event) {
        EventRecordEntity eventRecordEntity;
        try {
            eventRecordEntity = EventRecordEntity.fromEvent(event, objectMapper);
        } catch (JsonProcessingException e) {
            throw new InternalServerException("이벤트 객체 직렬화에 실패 했습니다.", e);
        }
        eventRecordJpaRepository.save(eventRecordEntity);
    }

    @Override
    public void markAsPublished(Long id) {
        Optional<EventRecordEntity> eventRecord = eventRecordJpaRepository.findById(id);
        eventRecord.ifPresent(EventRecordEntity::setPublishedTrue);
    }

    @Override
    public <T> Optional<EventRecord<T>> findByTransactionId(String transactionId, Class<T> clazz) {
        return eventRecordJpaRepository.findByTransactionId(transactionId)
                .flatMap(entity -> {
                    try {
                        return Optional.of(EventRecordEntity.toDomain(entity, clazz, objectMapper));
                    } catch (JsonProcessingException e) {
                        return Optional.empty();
                    }
                });
    }
}
