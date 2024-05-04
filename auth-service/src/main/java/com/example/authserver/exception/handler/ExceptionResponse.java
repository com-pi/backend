package com.example.authserver.exception.handler;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ExceptionResponse {

    private Integer errorCode;
    private String errorMessage;

}
