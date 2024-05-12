package com.example.apigateway.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.apigateway.domain.Passport;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

@Component
public class JwtValidator {

    @Value("${secret.accessToken}")
    private String accessTokenSecret;


    @SuppressWarnings("unused")
    public Optional<Passport> validateToken(String token) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(accessTokenSecret)).build().verify(token);
        if (decodedJWT.getExpiresAt().toInstant().isAfter(Instant.now())) {
            return Optional.ofNullable(Passport.of(decodedJWT.getSubject(), decodedJWT.getClaim("rol").asString()));
        }
        return Optional.empty();
    }

}
