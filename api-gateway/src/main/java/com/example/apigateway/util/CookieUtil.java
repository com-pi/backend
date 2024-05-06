package com.example.apigateway.util;


import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseCookie;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;

public class CookieUtil {

    public static void removeRefreshTokenCookies(ServerWebExchange exchange) {
        exchange.getResponse().addCookie(
                ResponseCookie.from("RefreshToken", "")
                .maxAge(0)
                .httpOnly(true)
                .path("/").build());
    }

    public static Boolean hasRefreshTokenCookie(ServerWebExchange exchange) {
        List<HttpCookie> refreshTokenCookies = exchange.getRequest().getCookies().get("RefreshToken");
        if (refreshTokenCookies != null && !refreshTokenCookies.isEmpty()) {
            if (refreshTokenCookies.size() > 1) {
                removeRefreshTokenCookies(exchange);
                return false;
            }
            return true;
        }
        return false;
    }

}
