package com.example.authserver.exception;

import feign.FeignException;
import lombok.Getter;

@Getter
public class OAuthLoginException extends RuntimeException {

    private final FeignException feignException;
    private final String message;

    public OAuthLoginException(FeignException feignException) {
        this.feignException = feignException;
        this.message = feignException.contentUTF8();
    }

}
