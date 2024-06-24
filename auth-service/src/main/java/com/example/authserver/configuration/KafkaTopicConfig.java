package com.example.authserver.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

//@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic memberTopic(){
        return TopicBuilder
                .name(KafkaTopic.MEMBER_CQRS)
                .build();
    }

    @Bean
    public NewTopic cqrsTopic(){
        return TopicBuilder
                .name(KafkaTopic.MEMBER)
                .build();
    }

}
