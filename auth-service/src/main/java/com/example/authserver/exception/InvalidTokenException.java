package com.example.authserver.exception;

import com.example.authserver.domain.TokenType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class InvalidTokenException extends RuntimeException {

    private final TokenType tokenType;
    private final String message;

    public InvalidTokenException(TokenType tokenType) {
        this.tokenType = tokenType;
        this.message = tokenType.getInvalidMessage();
    }

    public InvalidTokenException() {
        this.tokenType = TokenType.REFRESH_TOKEN;
        this.message = TokenType.REFRESH_TOKEN.getInvalidMessage();
    }

}
