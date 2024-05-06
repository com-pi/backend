package com.example.apigateway.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.apigateway.domain.Passport;
import com.example.apigateway.exception.NoAccessTokenException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Optional;
import java.util.function.Function;

@Component
public class JwtValidator {

    @Value("${secret.accessToken}")
    private String accessTokenSecret;
    @Value("${secret.refreshToken}")
    private String refreshTokenSecret;


    @SuppressWarnings("unused")
    public Optional<Passport> validateToken(String token, TokenType tokenType) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(tokenType.getSecret(this))).build().verify(token);
        if (decodedJWT.getExpiresAt().toInstant().isBefore(Instant.now())) {
            return Optional.ofNullable(Passport.of(decodedJWT.getSubject(), decodedJWT.getClaim("rol").asString()));
        }
        return Optional.empty();
    }

    public Mono<Passport> validateTokenReactive(String token, TokenType tokenType) {
        return Mono.fromCallable(() -> {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(tokenType.getSecret(this))).build().verify(token);
            if (decodedJWT.getExpiresAt().toInstant().isBefore(Instant.now())) {
                return Passport.of(decodedJWT.getSubject(), decodedJWT.getClaim("rol").asString());
            }
            throw new NoAccessTokenException();
        });
    }


    public enum TokenType {
        ACCESS_TOKEN(JwtValidator -> JwtValidator.accessTokenSecret),
        REFRESH_TOKEN(JwtValidator -> JwtValidator.refreshTokenSecret);

        private final Function<JwtValidator, String> getSecret;

        TokenType(Function<JwtValidator, String> getSecret) {
            this.getSecret = getSecret;
        }

        public byte[] getSecret(JwtValidator jwtValidator){
            return getSecret.apply(jwtValidator).getBytes(StandardCharsets.UTF_8);
        }
    }

}
