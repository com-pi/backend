package com.example.authserver.application;

import com.example.authserver.adapter.in.response.TokenReIssueResponse;
import com.example.authserver.application.port.out.persistence.RedisPort;
import com.example.authserver.domain.ComPToken;
import com.example.authserver.exception.InvalidTokenException;
import com.example.authserver.util.CookieUtil;
import com.example.authserver.adapter.util.JwtUtilImpl;
import com.example.common.domain.Passport;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.authserver.domain.TokenType.ACCESS_TOKEN;
import static com.example.authserver.domain.TokenType.REFRESH_TOKEN;

@Service
@RequiredArgsConstructor
public class TokenReissueService {

    private final RedisPort redisPort;
    private final JwtUtilImpl jwtUtil;

    public TokenReIssueResponse reissueToken(HttpServletRequest request, HttpServletResponse response) {

        String refreshToken = CookieUtil.getRefreshToken(request)
                .orElseThrow(() -> new InvalidTokenException(REFRESH_TOKEN));
        Passport passport = jwtUtil.validateToken(refreshToken, REFRESH_TOKEN)
                .orElseThrow(() -> new InvalidTokenException(REFRESH_TOKEN));
        String storedToken = redisPort.getRefreshToken(passport)
                .orElseThrow(() -> new InvalidTokenException(REFRESH_TOKEN));

        if (refreshToken.equals(storedToken)) {
            ComPToken comPToken = jwtUtil.generateToken(passport, ACCESS_TOKEN);
            return TokenReIssueResponse.of(comPToken);
        }

        CookieUtil.removeRefreshTokenCookies(response);
        throw new InvalidTokenException(REFRESH_TOKEN);
    }
}
