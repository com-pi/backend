package com.example.authserver.exception.handler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class ExceptionResponse {

    private Integer errorCode;
    private String errorMessage;

}
