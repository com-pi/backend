package com.example.authserver.exception.handler;

import com.example.common.exception.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundException(NotFoundException exception) {

        return switch (exception.getResourceClass().getName()) {
            case "Member" ->
                    ResponseEntity.status(404).body(
                        new ExceptionResponse(404, "회원을 찾을 수 없습니다"));

            default ->
                    ResponseEntity.status(404).body(
                            new ExceptionResponse(404,  "Not Found"));
        };
    }

}
