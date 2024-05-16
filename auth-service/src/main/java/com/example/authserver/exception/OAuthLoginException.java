package com.example.authserver.exception;

import feign.FeignException;
import lombok.Getter;

@Getter
public class OAuthLoginException extends RuntimeException {
    public OAuthLoginException(FeignException feignException) {
        super(feignException);
    }

}
