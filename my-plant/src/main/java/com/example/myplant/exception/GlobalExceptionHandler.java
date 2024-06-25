package com.example.myplant.exception;

import com.example.common.baseentity.CommonResponse;
import com.example.common.exception.ConflictException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<CommonResponse<Void>> handleConflictException(ConflictException exception) {
        return CommonResponse.conflictWithMessage(exception.getMessage());
    }
}
