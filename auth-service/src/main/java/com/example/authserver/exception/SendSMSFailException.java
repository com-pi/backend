package com.example.authserver.exception;

import com.example.common.exception.SimpleMessageException;

public class SendSMSFailException extends SimpleMessageException {
    public SendSMSFailException(String message) {
        super(message);
    }
}
