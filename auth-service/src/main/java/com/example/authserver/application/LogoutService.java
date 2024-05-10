package com.example.authserver.application;

import com.example.authserver.application.port.out.persistence.RedisPort;
import com.example.authserver.domain.TokenType;
import com.example.authserver.util.CookieUtil;
import com.example.authserver.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService {

    private final RedisPort redisPort;
    private final JwtUtil jwtUtil;

    public void logout(
            HttpServletRequest request,
            HttpServletResponse response) {

        CookieUtil.getRefreshToken(request)
                .flatMap(refreshToken -> jwtUtil.validateToken(refreshToken, TokenType.REFRESH_TOKEN))
                .ifPresent(redisPort::removeRefreshToken);

        CookieUtil.removeRefreshTokenCookies(response);
    }

}
