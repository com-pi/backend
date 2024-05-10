package com.example.authserver.exception;

import lombok.Getter;

@Getter
public class AlreadyLoggedInException extends RuntimeException {

    private final String message;

    public AlreadyLoggedInException(String message) {
        super(message);
        this.message = message;
    }
}
