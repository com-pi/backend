package com.example.authserver.application.port.in;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface MessageConsumeUseCase {

    void consumeMemberCqrsMessage(ConsumerRecord<String, String> message);

}
