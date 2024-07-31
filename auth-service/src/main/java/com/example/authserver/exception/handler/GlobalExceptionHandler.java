package com.example.authserver.exception.handler;

import com.example.authserver.exception.*;
import com.example.common.baseentity.CommonResponse;
import com.example.common.exception.ConflictException;
import com.example.common.exception.NotFoundException;
import com.example.common.exception.UnauthorizedException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final ObjectMapper objectMapper;

    // Todo : 에러 로깅

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<CommonResponse<Void>> handleNotFoundException(NotFoundException exception) {
        log.error(exception.getMessage(), exception);
        return switch (exception.getResourceClass().getName()) {
            case "Member" -> CommonResponse.notFoundWithMessage("회원을 찾을 수 없습니다.");
            case "MemberDocument" -> CommonResponse.notFoundWithMessage("회원 도큐먼트를 찾을 수 없습니다.");
            default -> CommonResponse.notFoundWithMessage("Not Found");
        };
    }

    @ExceptionHandler(OAuthLoginException.class)
    public ResponseEntity<CommonResponse<Void>> handleFeignException(OAuthLoginException exception) {
        log.error(exception.getMessage(), exception);
        String message = exception.getMessage();
        try {
            JsonNode jsonNode = objectMapper.readTree(exception.getMessage());
            message = jsonNode.path("message").asText();
        } catch (JsonProcessingException ignored) {
        }
        return CommonResponse.unauthorizedWithMessage("소셜 로그인 중 예외 발생 : " + message);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<CommonResponse<List<String>>> handleBindException(BindException exception) {
        List<String> errors = exception.getBindingResult().getAllErrors().stream()
                .map(e -> {
                    if (e instanceof FieldError) {
                        return ((FieldError) e).getField() + ": " + e.getDefaultMessage();
                    } else {
                        return e.getObjectName() + ": " + e.getDefaultMessage();
                    }
                })
                .collect(Collectors.toList());

        CommonResponse<List<String>> response = new CommonResponse<>("Validation failed", errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<CommonResponse<Void>> handleInvalidTokenException(InvalidTokenException exception) {
        log.error(exception.getMessage(), exception);
        return CommonResponse.badRequestWithMessage(exception.getTokenType().getInvalidMessage());
    }

    @ExceptionHandler(AlreadyLoggedInException.class)
    public ResponseEntity<CommonResponse<Void>> handleAlreadyLoggedInException(AlreadyLoggedInException exception) {
        log.error(exception.getMessage(), exception);
        return CommonResponse.conflictWithMessage(exception.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<CommonResponse<Void>> handleUnauthorizedException(UnauthorizedException exception) {
        log.error(exception.getMessage(), exception);
        return CommonResponse.unauthorizedWithMessage(exception.getMessage());
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<CommonResponse<Void>> handleForbiddenException(ForbiddenException exception) {
        log.error(exception.getMessage(), exception);
        return CommonResponse.forbiddenWithMessage(exception.getMessage());
    }

    @ExceptionHandler(VerificationFailException.class)
    public ResponseEntity<CommonResponse<Void>> handleVerificationFailException(VerificationFailException exception) {
        log.error(exception.getMessage(), exception);
        return CommonResponse.forbiddenWithMessage(exception.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<CommonResponse<Void>> handleBadRequestException(BadRequestException exception) {
        log.error(exception.getMessage(), exception);
        return CommonResponse.badRequestWithMessage(exception.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<CommonResponse<Void>> handleRuntimeException(RuntimeException exception) {
        log.error(exception.getMessage(), exception);
        return CommonResponse.internalServerErrorWithMessage(exception.getMessage());
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<CommonResponse<Void>> handleConflictException(ConflictException exception) {
        log.error(exception.getMessage(), exception);
        return CommonResponse.conflictWithMessage(exception.getMessage());
    }

}
