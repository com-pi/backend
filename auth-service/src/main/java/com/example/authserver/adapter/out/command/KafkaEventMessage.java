package com.example.authserver.adapter.out.command;

public class KafkaEventMessage {
    private String id;
    private String eventType;
    private String entityType;
    private String entityId;
    private Long createAt;
    private String data;
}
