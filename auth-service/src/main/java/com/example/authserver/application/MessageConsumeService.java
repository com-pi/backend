package com.example.authserver.application;

import com.example.authserver.application.port.in.MessageConsumeUseCase;
import com.example.authserver.domain.Event;
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

    @Override
    public void consumeMemberCqrsMessage(ConsumerRecord<String, String> message) {

        String messageString = message.value();
        try {
            Event<Member> event = objectMapper.readValue(messageString,  new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException("메세지 역직렬화에 실패했습니다.", e);
        }
    }

}
