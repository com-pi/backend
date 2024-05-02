package com.example.authserver.common;

import com.example.authserver.domain.JWT;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class JwtUtil {

    private static final String TOKEN_ISSUER = "꼼삐";

    public JWT createAccessToken() {
        return com.auth0.jwt.JWT.create().withIssuer(TOKEN_ISSUER)
                .withExpiresAt(Instant.now().plus(20, ChronoUnit.MINUTES))
                .withSubject()
                .sign()
    }

}
