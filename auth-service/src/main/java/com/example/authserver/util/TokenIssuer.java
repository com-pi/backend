package com.example.authserver.util;

import com.example.authserver.application.port.out.persistence.RedisPort;
import com.example.authserver.application.util.JwtUtil;
import com.example.authserver.domain.ComppiToken;
import com.example.authserver.domain.Member;
import com.example.authserver.domain.TokenType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenIssuer {

    private final RedisPort redisPort;
    private final JwtUtil jwtUtil;

    public TokenPair issueToken(Member member) {
        ComppiToken accessToken = jwtUtil.generateToken(member, TokenType.ACCESS_TOKEN);
        ComppiToken refreshToken = jwtUtil.generateToken(member, TokenType.REFRESH_TOKEN);
        redisPort.saveRefreshToken(member, refreshToken);
        return new TokenPair(accessToken, refreshToken);
    }
}
