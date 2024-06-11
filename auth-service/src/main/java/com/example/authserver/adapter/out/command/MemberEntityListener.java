package com.example.authserver.adapter.out.command;

import com.example.authserver.adapter.out.MemberEntity;
import com.example.authserver.domain.Event;
import com.example.authserver.domain.EventType;
import com.example.authserver.domain.Member;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MemberEntityListener {

    private final MemberEventPublisher eventPublisher;

    @PrePersist
    public void prePersist(MemberEntity member) {
        eventPublisher.publishEvent(createEvent(EventType.CREATE, member));
    }

    @PostUpdate
    public void postUpdate(MemberEntity employee) {
        eventPublisher.publishEvent(createEvent(EventType.UPDATE, employee));
    }

    @PreRemove
    public void preRemove(MemberEntity employee) {
        eventPublisher.publishEvent(createEvent(EventType.DELETE, employee));
    }

    public Event<MemberEntity> createEvent(EventType eventType, MemberEntity data) {
        return Event.<MemberEntity>builder()
                .source(this)
                .transactionId(UUID.randomUUID().toString())
                .eventType(eventType)
                .entityId(EventType.CREATE.equals(eventType) ? null : data.getId().toString())
                .entityType(Member.class.getName())
                .data(data)
                .build();
    }





}
