package com.example.encycloservice.exception.handler;

import com.example.common.baseentity.CommonResponse;
import com.example.common.exception.CommonException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<CommonResponse<Void>> handleNotFoundException(CommonException exception) {
        log.info(exception.getMessage(), exception);
        return switch (HttpStatus.resolve(exception.getHttpStatus())) {
            case NOT_FOUND -> CommonResponse.notFoundWithMessage(exception.getMessage());
            case FORBIDDEN -> CommonResponse.forbiddenWithMessage(exception.getMessage());
            case UNAUTHORIZED -> CommonResponse.unauthorizedWithMessage(exception.getMessage());
            case INTERNAL_SERVER_ERROR -> CommonResponse.internalServerErrorWithMessage(exception.getMessage());
            default -> CommonResponse.okWithMessage("에러 발생");
        };
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<CommonResponse<List<String>>> handleBindException(BindException exception) {
        log.info(exception.getMessage(), exception);
        List<String> errors = exception.getBindingResult().getAllErrors().stream()
                .map(e -> {
                    if (e instanceof FieldError) {
                        return ((FieldError) e).getField() + ": " + e.getDefaultMessage();
                    } else {
                        return e.getObjectName() + ": " + e.getDefaultMessage();
                    }
                })
                .collect(Collectors.toList());

        CommonResponse<List<String>> response = new CommonResponse<>("유효성 검사 실패", errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


}
