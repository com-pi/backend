package com.example.authserver.exception;


import com.example.common.exception.SimpleMessageException;


public class VerificationFailException extends SimpleMessageException {

    public VerificationFailException(String message) {
        super(message);
    }

}
