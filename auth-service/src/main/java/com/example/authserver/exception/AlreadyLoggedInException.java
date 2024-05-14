package com.example.authserver.exception;

import com.example.common.exception.SimpleMessageException;

public class AlreadyLoggedInException extends SimpleMessageException {

    public AlreadyLoggedInException(String message) {
        super(message);
    }

}
