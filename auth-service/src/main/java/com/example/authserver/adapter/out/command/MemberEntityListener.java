package com.example.authserver.adapter.out.command;

import com.example.authserver.adapter.out.entity.MemberEntity;
import com.example.authserver.adapter.out.repository.MemberCacheRepository;
import com.example.authserver.domain.Event;
import com.example.authserver.domain.EventType;
import com.example.authserver.domain.Member;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MemberEntityListener {

//    private final MemberEventPublisher memberEventPublisher;
    private final MemberCacheRepository memberCacheRepository;

    @PrePersist
    public void prePersist(MemberEntity member) {
//        memberEventPublisher.publishEvent(createEvent(EventType.CREATE, member));
    }

    @PreUpdate
    public void preUpdate(MemberEntity member) {
//        memberEventPublisher.publishEvent(createEvent(EventType.UPDATE, member));
    }

    @PreRemove
    public void preRemove(MemberEntity member) {
//        memberEventPublisher.publishEvent(createEvent(EventType.DELETE, member));
    }

    @Async
    @PostUpdate
    public void postUpdate(MemberEntity member) {
        memberCacheRepository.saveMemberBrief(MemberEntity.toBrief(member));
    }

    @Async
    @PostRemove
    public void postRemove(MemberEntity member) {
        memberCacheRepository.deleteMemberBrief(member.getId());
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
