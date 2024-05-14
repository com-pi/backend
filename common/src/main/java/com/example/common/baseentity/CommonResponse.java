package com.example.common.baseentity;

import org.springframework.http.ResponseEntity;

public record CommonResponse (
        String message
){
    public static ResponseEntity<CommonResponse> okWithMessage(String message) {
        return ResponseEntity.ok(new CommonResponse(message));
    }

    @SuppressWarnings("unused")
    public static ResponseEntity<CommonResponse> forbiddenWithMessage(String message) {
        return ResponseEntity.status(403).body(new CommonResponse(message));
    }

    public static ResponseEntity<CommonResponse> notFoundWithMessage(String message) {
        return ResponseEntity.status(404).body(new CommonResponse(message));
    }

    public static ResponseEntity<CommonResponse> conflictWithMessage(String message) {
        return ResponseEntity.status(409).body(new CommonResponse(message));
    }

    public static ResponseEntity<CommonResponse> unauthorizedWithMessage(String message) {
        return ResponseEntity.status(401).body(new CommonResponse(message));
    }

    public static ResponseEntity<CommonResponse> badRequestWithMessage(String message) {
        return ResponseEntity.status(400).body(new CommonResponse(message));
    }

    public static ResponseEntity<CommonResponse> internalServerErrorWithMessage(String message) {
        return ResponseEntity.status(500).body(new CommonResponse(message));
    }

}
