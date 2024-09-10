package com.example.authserver.util;

import com.example.authserver.domain.ComppiToken;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Arrays;
import java.util.Optional;

public class CookieUtil {

    public static void setRefreshCookie(ComppiToken comppiToken, HttpServletResponse response) {
        response.addCookie(comppiToken.generateRefreshTokenCookie());
    }

    public static Optional<String> getRefreshToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            return Arrays.stream(cookies)
                    .filter(cookie -> cookie.getName().equals("RefreshToken"))
                    .findFirst()
                    .flatMap(cookie -> Optional.ofNullable(cookie.getValue()));
        }
        return Optional.empty();
    }

    public static void removeRefreshTokenCookies(HttpServletResponse response) {
        response.addCookie(ComppiToken.removeRefreshTokenCookie());
    }

}
