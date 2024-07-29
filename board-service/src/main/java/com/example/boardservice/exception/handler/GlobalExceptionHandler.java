package com.example.boardservice.exception.handler;

import com.example.boardservice.exception.DuplicateLikeException;
import com.example.common.baseentity.CommonResponse;
import com.example.common.exception.InternalServerException;
import com.example.common.exception.NotFoundException;
import com.example.common.exception.UnauthorizedException;
import com.example.imagemodule.exception.FileUploadException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Todo : 에러 로깅

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<CommonResponse<Void>> handleNotFoundException(NotFoundException exception) {
        return switch (exception.getMissingElement()) {
            case "Member" -> CommonResponse.notFoundWithMessage("회원을 찾을 수 없습니다.");
            default -> CommonResponse.notFoundWithMessage(exception.getMessage());
        };
    }

    @ExceptionHandler(FileUploadException.class)
    public ResponseEntity<CommonResponse<Void>> handleInvalidTokenException(FileUploadException exception) {
        return CommonResponse.badRequestWithMessage(exception.getMessage());
    }

    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<CommonResponse<Void>> handleInternalServerException(InternalServerException exception) {
        return CommonResponse.internalServerErrorWithMessage(exception.getMessage());
    }

    @ExceptionHandler(DuplicateLikeException.class)
    public ResponseEntity<CommonResponse<Void>> handleDuplicatedLikeException(DuplicateLikeException exception) {
        return CommonResponse.conflictWithMessage(exception.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<CommonResponse<Void>> handleUnauthorizedException(UnauthorizedException exception) {
        return CommonResponse.unauthorizedWithMessage(exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public void occuredExeption(Exception e){
        e.printStackTrace();
    }
}
