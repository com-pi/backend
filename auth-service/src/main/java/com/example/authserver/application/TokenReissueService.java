package com.example.authserver.application;

import com.example.authserver.adapter.in.response.TokenReIssueResponse;
import com.example.authserver.util.JwtUtilImpl;
import com.example.authserver.application.port.out.persistence.RedisPort;
import com.example.authserver.domain.ComppiToken;
import com.example.authserver.exception.InvalidTokenException;
import com.example.authserver.util.CookieUtil;
import com.example.common.domain.Passport;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.authserver.domain.TokenType.ACCESS_TOKEN;
import static com.example.authserver.domain.TokenType.REFRESH_TOKEN;

@Service
@RequiredArgsConstructor
public class TokenReissueService {

    private final RedisPort redisPort;
    private final JwtUtilImpl jwtUtil;

    public TokenReIssueResponse reissueToken(HttpServletRequest request, HttpServletResponse response) {

        String refreshToken = CookieUtil.getRefreshToken(request)
                .orElseThrow(() -> new InvalidTokenException("리프레시 토큰이 없습니다.", REFRESH_TOKEN));
        Passport passport = jwtUtil.validateToken(refreshToken, REFRESH_TOKEN)
                .orElseThrow(() -> new InvalidTokenException("리프레시 토큰이 유효하지 않습니다.", REFRESH_TOKEN));
        Optional<String> storedToken = redisPort.getRefreshToken(passport);
        if(storedToken.isEmpty()) {
            CookieUtil.removeRefreshTokenCookies(response);
            throw new InvalidTokenException("검증되지 않은 리프레시 토큰입니다.", REFRESH_TOKEN);
        }

        if (refreshToken.equals(storedToken.get())) {
            ComppiToken comppiToken = jwtUtil.generateToken(passport, ACCESS_TOKEN);
            return TokenReIssueResponse.of(comppiToken);
        }

        CookieUtil.removeRefreshTokenCookies(response);
        throw new InvalidTokenException("리프레시 토큰 에러", REFRESH_TOKEN);
    }
}
