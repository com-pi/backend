package com.example.authserver.exception;

import com.example.authserver.domain.TokenType;
import lombok.Getter;

@Getter
public class InvalidTokenException extends RuntimeException {

    private final TokenType tokenType;

    public InvalidTokenException(TokenType tokenType) {
        super(tokenType.getInvalidMessage());
        this.tokenType = tokenType;
    }

    public InvalidTokenException(TokenType tokenType, String message) {
        super(message);
        this.tokenType = tokenType;
    }

}
