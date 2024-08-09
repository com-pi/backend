package com.example.authserver.exception;

import com.example.authserver.domain.TokenType;
import lombok.Getter;

@Getter
public class InvalidTokenException extends RuntimeException {

    private final TokenType tokenType;

    public InvalidTokenException(String message, TokenType tokenType) {
        super(message);
        this.tokenType = tokenType;
    }

}
