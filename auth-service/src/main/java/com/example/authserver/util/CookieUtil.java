package com.example.authserver.util;

import com.example.authserver.domain.ComPToken;
import com.example.authserver.exception.InvalidTokenException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Arrays;

import static com.example.authserver.util.JwtUtil.TokenType.REFRESH_TOKEN;

public class CookieUtil {

    public static void setRefreshCookie(ComPToken comPToken, HttpServletResponse response) {
        response.addCookie(comPToken.removeRefreshTokenCookie());
        response.setHeader("Set-Cookie", comPToken.generateRefreshTokenCookie());
    }

    public static String getRefreshToken(HttpServletRequest request) {
        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals("RefreshToken"))
                // 게이트웨이에서 리프레시 토큰을 가지고 있는지 검증하기 때문에 NonNull
                .findFirst().orElseThrow(() -> new InvalidTokenException(REFRESH_TOKEN))
                .getValue();
    }

}
