package com.example.authserver.util;

import com.example.authserver.application.port.out.persistence.RedisPort;
import com.example.authserver.domain.ComPToken;
import com.example.authserver.domain.Member;
import com.example.authserver.domain.TokenType;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordAuthenticatorImpl implements PasswordAuthenticator {

    private final RedisPort redisPort;
    private final JwtUtilImpl jwtUtil;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    @Nullable
    public AuthenticateResponse authenticate(Member member, String password) {
        boolean isMatch = passwordEncoder.matches(password, member.getPassword());
        if (!isMatch) {
            return null;
        }
        ComPToken accessToken = jwtUtil.generateToken(member, TokenType.ACCESS_TOKEN);
        ComPToken refreshToken = jwtUtil.generateToken(member, TokenType.REFRESH_TOKEN);
        redisPort.saveRefreshToken(member, refreshToken);
        return new AuthenticateResponse(accessToken, refreshToken, member.getLastLogin() == null);
    }

}
