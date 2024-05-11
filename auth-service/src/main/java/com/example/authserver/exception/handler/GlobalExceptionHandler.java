package com.example.authserver.exception.handler;

import com.example.authserver.exception.*;
import com.example.common.baseentity.CommonResponse;
import com.example.common.exception.NotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    // Todo : 에러 로깅

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<CommonResponse> handleNotFoundException(NotFoundException exception) {

        return switch (exception.getMissingElement()) {
            case "Member" -> CommonResponse.notFoundWithMessage("회원을 찾을 수 없습니다.");
            case "인증코드" -> CommonResponse.notFoundWithMessage("인증코드가 없거나 만료되었습니다.");
            default -> CommonResponse.notFoundWithMessage("Not Found");
        };
    }

    @ExceptionHandler(OAuthLoginException.class)
    public ResponseEntity<CommonResponse> handleFeignException(OAuthLoginException exception) {
        String message = exception.getMessage();
        try {
            JsonNode jsonNode = objectMapper.readTree(exception.getMessage());
            message = jsonNode.path("message").asText();
        } catch (JsonProcessingException ignored) {
        }
        return CommonResponse.unauthorizedWithMessage("소셜 로그인 중 예외 발생 : " + message);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<CommonResponse> handleInvalidTokenException(InvalidTokenException exception) {
        return CommonResponse.badRequestWithMessage(exception.getTokenType().getInvalidMessage());
    }

    @ExceptionHandler(AlreadyLoggedInException.class)
    public ResponseEntity<CommonResponse> handleAlreadyLoggedInException(AlreadyLoggedInException exception) {
        return CommonResponse.conflictWithMessage(exception.getMessage());
    }

    @ExceptionHandler(VerificationFailException.class)
    public ResponseEntity<CommonResponse> handleSimpleMessageException(VerificationFailException exception) {
        return CommonResponse.badRequestWithMessage(exception.getMessage());
    }

    @ExceptionHandler(SendSMSFailException.class)
    public ResponseEntity<CommonResponse> handleSimpleMessageException(SendSMSFailException exception) {
        return CommonResponse.badRequestWithMessage(exception.getMessage());
    }


}
