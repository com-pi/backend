package com.example.authserver.adapter.out.command;

import com.example.authserver.domain.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberEventPublisher {

    private final ApplicationEventPublisher eventPublisher;

    public void publishEvent(Event<MemberEntity> event) {
        eventPublisher.publishEvent(event);
    }

}
