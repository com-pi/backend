package com.example.common.exception;

import lombok.Getter;

@Getter
public class SimpleMessageException extends RuntimeException {

    private final String message;

    public SimpleMessageException(String message) {
        this.message = message;
    }
}
