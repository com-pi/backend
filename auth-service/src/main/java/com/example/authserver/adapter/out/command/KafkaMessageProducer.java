package com.example.authserver.adapter.out.command;

import com.example.authserver.domain.Event;
import com.example.common.exception.InternalServerException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;

import java.util.Properties;

//@Component
@RequiredArgsConstructor
public class KafkaMessageProducer {

    private KafkaProducer<String, String> kafkaProducer;
    private final ObjectMapper objectMapper;

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @PostConstruct
    private void init(){
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        this.kafkaProducer = new KafkaProducer<>(props);
    }

    public <T> void sendMessage(String topic, Event<T> event) {
        try {
            String eventMessage = objectMapper.writeValueAsString(event);
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, event.getEntityId(), eventMessage);
            kafkaProducer.send(record);
        } catch (JsonProcessingException e) {
            throw new InternalServerException("이벤트 객체 직렬화에 실패했습니다.", e);
        }
    }




}
