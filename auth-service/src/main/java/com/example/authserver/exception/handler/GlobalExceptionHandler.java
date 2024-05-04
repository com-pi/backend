package com.example.authserver.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Todo 에러 핸들링
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception e) {
        return ResponseEntity.ok(
                new ExceptionResponse(200, "에러 발생"));
    }

    // Todo 바인딩 익셉션 관련 Common 모듈에 정의하고 상속 받기

}
