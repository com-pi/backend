package com.example.authserver.adapter.in.response;

import java.time.LocalDateTime;

public record LoginResponse(
        String accessToken,
        LocalDateTime lastLoginAt
) {
    public static LoginResponse of(String accessToken, LocalDateTime lastLoginAt){
        return new LoginResponse(accessToken, lastLoginAt);
    }
}
