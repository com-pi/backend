package com.example.authserver.exception;

import com.example.authserver.util.JwtUtil;

public class InvalidTokenException extends RuntimeException {

    JwtUtil.TokenType tokenType;
    String message;

    public InvalidTokenException(JwtUtil.TokenType tokenType) {
        this.tokenType = tokenType;
        this.message = tokenType.getInvalidMessage();
    }

}
