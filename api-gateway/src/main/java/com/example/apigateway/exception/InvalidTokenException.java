package com.example.apigateway.exception;

import lombok.Getter;

@Getter
public class InvalidTokenException extends RuntimeException {

    public static int STATUS = 401;

    public InvalidTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidTokenException(String message) {
        super(message);
    }

}
