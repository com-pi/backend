package com.example.apigateway.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionResponse {

    private final String message;

    public static ExceptionResponse of(RuntimeException exception) {
        return new ExceptionResponse(exception.getMessage());
    }

    public static ExceptionResponse of(String message) {
        return new ExceptionResponse(message);
    }

}