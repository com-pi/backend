package com.example.authserver.application;

import com.example.authserver.adapter.in.TokenReIssueResponse;
import com.example.authserver.application.port.out.persistence.RedisPort;
import com.example.authserver.domain.ComPToken;
import com.example.authserver.exception.InvalidTokenException;
import com.example.authserver.util.JwtUtil;
import com.example.common.domain.Passport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.authserver.util.JwtUtil.TokenType.ACCESS_TOKEN;
import static com.example.authserver.util.JwtUtil.TokenType.REFRESH_TOKEN;

@Service
@RequiredArgsConstructor
public class TokenReissueService {

    private final RedisPort redisPort;
    private final JwtUtil jwtUtil;

    public TokenReIssueResponse reissueToken(String refreshToken) {

        Passport tokenHolder = jwtUtil.validateToken(refreshToken, REFRESH_TOKEN).orElseThrow(()
                -> new InvalidTokenException(REFRESH_TOKEN));

        String storedToken = redisPort.getRefreshToken(tokenHolder);

        if(storedToken != null && storedToken.equals(refreshToken)) {
            ComPToken comPToken = jwtUtil.generateToken(tokenHolder, ACCESS_TOKEN);
            return TokenReIssueResponse.of(comPToken);
        }

        throw new InvalidTokenException(REFRESH_TOKEN);
    }


}
