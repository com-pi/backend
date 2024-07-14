package com.example.authserver.application.port.out.external.response;

public record NaverTokenResponse(
        String refresh_token,
        String token_type,
        Integer expires_in,
        String error,
        String access_token,
        String error_description
){}
