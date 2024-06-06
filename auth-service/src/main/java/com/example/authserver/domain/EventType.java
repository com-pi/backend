package com.example.authserver.domain;

import lombok.Getter;

@Getter
public enum EventType {

    CREATE("CREATE"),
    UPDATE("UPDATE"),
    DELETE("DELETE");

    private final String name;

    EventType(String name) {
        this.name = name;
    }

}
