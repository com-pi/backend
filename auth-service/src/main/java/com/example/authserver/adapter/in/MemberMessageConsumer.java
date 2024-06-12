package com.example.authserver.adapter.in;

import com.example.authserver.application.port.in.MessageConsumeUseCase;
import com.example.authserver.configuration.KafkaTopic;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberMessageConsumer {

    private final MessageConsumeUseCase messageConsumeUseCase;

    @KafkaListener(topics = KafkaTopic.MEMBER_CQRS, groupId = "auth")
    public void memberMessageReceive(ConsumerRecord<String, String> record) {
        log.info("Received member changed: {}", record.value());
        messageConsumeUseCase.consumeMemberCqrsMessage(record);
    }


}
