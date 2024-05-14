package com.example.apigateway.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidAccessTokenException extends RuntimeException {

    public InvalidAccessTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
