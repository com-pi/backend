package com.example.authserver.domain;

import jakarta.servlet.http.Cookie;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ComPToken {

    private final String token;

    public static ComPToken of(String token) {
        return new ComPToken(token);
    }

    public String generateRefreshTokenCookie() {
        return String.format("RefreshToken=%s; Secure; Path=/; HttpOnly;", token);
    }

    public Cookie removeRefreshTokenCookie() {
        Cookie deleteCookie = new Cookie("RefreshToken", "");
        deleteCookie.setMaxAge(0);
        return deleteCookie;
    }

}
