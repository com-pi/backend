package com.example.myplant.exception;

import com.example.common.baseentity.CommonResponse;
import com.example.common.exception.ConflictException;
import com.example.common.exception.NotFoundException;
import com.example.common.exception.UnauthorizedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<CommonResponse<Void>> handleConflictException(ConflictException exception) {
        return CommonResponse.conflictWithMessage(exception.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<CommonResponse<Void>> handleNotFoundException(NotFoundException exception) {
        return CommonResponse.notFoundWithMessage(exception.getResourceClass().getSimpleName() + " Not Found");
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<CommonResponse<Void>> handleUnauthorizedException(UnauthorizedException exception) {
        return CommonResponse.unauthorizedWithMessage(exception.getMessage());
    }
}
