package com.example.authserver.domain;

import jakarta.servlet.http.Cookie;
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

    public Cookie generateRefreshTokenCookie() {
        Cookie refreshTokenCookie = new Cookie("RefreshToken", token);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setDomain("comppi.site");
        refreshTokenCookie.setMaxAge(REFRESH_TOKEN.getSeconds());
        return refreshTokenCookie;
    }

    public static Cookie removeRefreshTokenCookie() {
        Cookie removeCookie = new Cookie("RefreshToken", "");
        removeCookie.setSecure(true);
        removeCookie.setHttpOnly(true);
        removeCookie.setPath("/");
        removeCookie.setDomain("comppi.site");
        removeCookie.setMaxAge(0);
        return removeCookie;
    }

}
