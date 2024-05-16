package com.example.common.baseentity;

import org.springframework.http.ResponseEntity;

public record CommonResponse<T> (
        String message,
        T data
){
    public static <K> ResponseEntity<CommonResponse<K>> okWithMessage(String message, K data) {
        return ResponseEntity.ok(new CommonResponse<>(message, data));
    }

    public static ResponseEntity<CommonResponse<Void>> okWithMessage(String message) {
        return CommonResponse.okWithMessage(message, null);
    }


    public static <K> ResponseEntity<CommonResponse<K>> forbiddenWithMessage(String message, K data) {
        return ResponseEntity.status(403).body(new CommonResponse<>(message, data));
    }

    public static ResponseEntity<CommonResponse<Void>> forbiddenWithMessage(String message) {
        return CommonResponse.forbiddenWithMessage(message, null);
    }


    public static <K> ResponseEntity<CommonResponse<K>> notFoundWithMessage(String message, K data) {
        return ResponseEntity.status(404).body(new CommonResponse<>(message, data));
    }

    public static ResponseEntity<CommonResponse<Void>> notFoundWithMessage(String message) {
        return CommonResponse.notFoundWithMessage(message, null);
    }


    public static <K> ResponseEntity<CommonResponse<K>> conflictWithMessage(String message, K data) {
        return ResponseEntity.status(409).body(new CommonResponse<>(message, data));
    }

    public static ResponseEntity<CommonResponse<Void>> conflictWithMessage(String message) {
        return CommonResponse.conflictWithMessage(message, null);
    }

    public static <K> ResponseEntity<CommonResponse<K>> unauthorizedWithMessage(String message, K data) {
        return ResponseEntity.status(401).body(new CommonResponse<>(message, data));
    }

    public static ResponseEntity<CommonResponse<Void>> unauthorizedWithMessage(String message) {
        return CommonResponse.unauthorizedWithMessage(message, null);
    }


    public static <K> ResponseEntity<CommonResponse<K>> badRequestWithMessage(String message, K data) {
        return ResponseEntity.status(400).body(new CommonResponse<>(message, data));
    }

    public static ResponseEntity<CommonResponse<Void>> badRequestWithMessage(String message) {
        return CommonResponse.badRequestWithMessage(message, null);
    }


    public static <K> ResponseEntity<CommonResponse<K>> internalServerErrorWithMessage(String message, K data) {
        return ResponseEntity.status(500).body(new CommonResponse<>(message, data));
    }

    public static ResponseEntity<CommonResponse<Void>> internalServerErrorWithMessage(String message) {
        return CommonResponse.internalServerErrorWithMessage(message, null);
    }


}
