package com.example.authserver.exception.handler;

import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
public class ExceptionResponse {

    private Integer errorCode;
    private String errorMessage;

}
