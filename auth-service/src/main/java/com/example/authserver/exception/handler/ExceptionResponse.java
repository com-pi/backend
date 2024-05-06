package com.example.authserver.exception.handler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class ExceptionResponse {

    private Integer errorCode;
    private String errorMessage;

}
