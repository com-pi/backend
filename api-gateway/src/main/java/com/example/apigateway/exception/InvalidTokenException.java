package com.example.apigateway.exception;

import com.example.apigateway.domain.TokenType;
import lombok.Getter;

@Getter
public class InvalidTokenException extends RuntimeException {

    private final TokenType tokenType;

    public InvalidTokenException(String message, TokenType tokenType, Throwable cause) {
        super(message, cause);
        this.tokenType = tokenType;
    }

}
