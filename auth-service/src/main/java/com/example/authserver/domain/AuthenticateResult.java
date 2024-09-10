package com.example.authserver.domain;

import com.example.authserver.adapter.in.response.LoginResponse;
import com.example.authserver.util.TokenPair;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record AuthenticateResult(
        HttpStatus result,
        TokenPair tokenPair,
        LocalDateTime lastLoginAt
) {
    public LoginResponse toLoginResponse() {
        return new LoginResponse(
                this.tokenPair.accessToken().getToken(),
                this.lastLoginAt
        );
    }

}
