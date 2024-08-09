package com.example.apigateway.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionResponse {

    private final int status;
    private final String message;

    public static ExceptionResponse of(HttpStatus status, Throwable exception) {
        return new ExceptionResponse(status.value(), exception.getMessage());
    }

    public static ExceptionResponse of(HttpStatus status, String message) {
        return new ExceptionResponse(status.value(), message);
    }

    public static ExceptionResponse of(int status, Throwable exception) {
        return new ExceptionResponse(status, exception.getMessage());
    }

    public static ExceptionResponse of(int status, String message) {
        return new ExceptionResponse(status, message);
    }


}