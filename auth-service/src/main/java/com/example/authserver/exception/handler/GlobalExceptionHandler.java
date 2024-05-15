package com.example.authserver.exception.handler;

import com.example.authserver.exception.AlreadyLoggedInException;
import com.example.authserver.exception.InvalidTokenException;
import com.example.authserver.exception.OAuthLoginException;
import com.example.common.baseentity.CommonResponse;
import com.example.common.exception.NotFoundException;
import com.example.common.exception.UnauthorizedException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.ForbiddenException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final ObjectMapper objectMapper;

    // Todo : 에러 로깅

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<CommonResponse<Void>> handleNotFoundException(NotFoundException exception) {

        return switch (exception.getResourceClass().getName()) {
            case "Member" -> CommonResponse.notFoundWithMessage("회원을 찾을 수 없습니다.");
            default -> CommonResponse.notFoundWithMessage("Not Found");
        };
    }

    @ExceptionHandler(OAuthLoginException.class)
    public ResponseEntity<CommonResponse<Void>> handleFeignException(OAuthLoginException exception) {
        String message = exception.getMessage();
        try {
            JsonNode jsonNode = objectMapper.readTree(exception.getMessage());
            message = jsonNode.path("message").asText();
        } catch (JsonProcessingException ignored) {
        }
        return CommonResponse.unauthorizedWithMessage("소셜 로그인 중 예외 발생 : " + message);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<CommonResponse<Void>> handleInvalidTokenException(InvalidTokenException exception) {
        return CommonResponse.badRequestWithMessage(exception.getTokenType().getInvalidMessage());
    }

    @ExceptionHandler(AlreadyLoggedInException.class)
    public ResponseEntity<CommonResponse<Void>> handleAlreadyLoggedInException(AlreadyLoggedInException exception) {
        return CommonResponse.conflictWithMessage(exception.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<CommonResponse<Void>> handleUnauthorizedException(UnauthorizedException exception) {
        return CommonResponse.unauthorizedWithMessage(exception.getMessage());
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<CommonResponse<Void>> handleForbiddenException(ForbiddenException exception) {
        return CommonResponse.forbiddenWithMessage(exception.getMessage());
    }

}
