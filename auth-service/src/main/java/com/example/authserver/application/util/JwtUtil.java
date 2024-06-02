package com.example.authserver.application.util;

import com.example.authserver.domain.ComPToken;
import com.example.authserver.domain.Member;
import com.example.authserver.domain.TokenType;
import com.example.common.domain.Passport;

import java.util.Optional;

public interface JwtUtil {
    ComPToken generateToken(Member member, TokenType tokenType);

    ComPToken generateToken(Passport passPort, TokenType tokenType);

    Optional<Passport> validateToken(String token, TokenType tokenType);
}
