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
}
