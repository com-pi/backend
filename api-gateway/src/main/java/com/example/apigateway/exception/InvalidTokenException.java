package com.example.apigateway.exception;

import com.example.apigateway.domain.TokenType;
import lombok.Getter;

@Getter
public class InvalidTokenException extends RuntimeException {


    public InvalidTokenException(TokenType tokenType, Throwable cause) {
        super(tokenType.getInvalidMessage(), cause);
    }

    public InvalidTokenException(String message, Throwable cause) {
        super(message, cause);
    }

}
