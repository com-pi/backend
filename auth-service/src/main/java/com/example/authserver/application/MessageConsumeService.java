package com.example.authserver.application;

import com.example.authserver.application.port.in.MessageConsumeUseCase;
import com.example.authserver.application.port.out.persistence.MemberCommand;
import com.example.authserver.domain.Event;
import com.example.authserver.domain.EventType;
import com.example.authserver.domain.Member;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageConsumeService implements MessageConsumeUseCase {

    private final ObjectMapper objectMapper;
    private final MemberCommand memberCommand;

    @Override
    public void consumeMemberCqrsMessage(ConsumerRecord<String, String> message) {

        String messageString = message.value();
        try {
            Event<Member> event = objectMapper.readValue(messageString,  new TypeReference<>() {});
            EventType eventType = event.getEventType();
            if(EventType.CREATE.equals(eventType)) {
                memberCommand.saveRead(event.getData());
            } else if(EventType.UPDATE.equals(eventType)) {
                memberCommand.updateRead(event.getData());
            }

        } catch (JsonProcessingException e) {
            throw new RuntimeException("메세지 역직렬화에 실패했습니다.", e);
        }
    }

}
