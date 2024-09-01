package com.example.authserver.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.example.authserver.domain.TokenType.REFRESH_TOKEN;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ComPToken {

    private final TokenType tokenType;
    private final String token;

    public static ComPToken of(TokenType tokenType, String token) {
        return new ComPToken(tokenType, token);
    }

    public String generateRefreshTokenCookie() {
        return String.format("RefreshToken=%s; Secure; Path=/; SameSite=None; HttpOnly; Max-Age=%s", token, REFRESH_TOKEN.getSeconds());
    }

    public static String removeRefreshTokenCookie() {
        return String.format("RefreshToken=; Secure; Path=/; SameSite=None; HttpOnly; Max-Age=%s", "0");
    }


}
