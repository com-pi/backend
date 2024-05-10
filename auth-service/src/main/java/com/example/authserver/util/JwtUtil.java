package com.example.authserver.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.authserver.domain.ComPToken;
import com.example.authserver.domain.Member;
import com.example.authserver.domain.TokenType;
import com.example.common.domain.Passport;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Getter
@Slf4j
public class JwtUtil {

    private static final String TOKEN_ISSUER = "꼼삐";
    @Value("${secret.accessToken}")
    private String accessTokenSecret;
    @Value("${secret.refreshToken}")
    private String refreshTokenSecret;


    public ComPToken generateToken(Member member, TokenType tokenType) {

        String token = JWT.create().withIssuer(TOKEN_ISSUER)
                .withExpiresAt(tokenType.getInstant())
                .withSubject(member.getId().toString())
                .withClaim("rol", member.getRole().name())
                .sign(Algorithm.HMAC256(
                        tokenType == TokenType.REFRESH_TOKEN ? refreshTokenSecret : accessTokenSecret
                ));

        return ComPToken.of(tokenType, token);
    }

    public ComPToken generateToken(Passport passPort, TokenType tokenType) {

        String token = JWT.create().withIssuer(TOKEN_ISSUER)
                .withExpiresAt(tokenType.getInstant())
                .withSubject(passPort.memberId().toString())
                .withClaim("rol", passPort.role().name())
                .sign(Algorithm.HMAC256(
                        tokenType == TokenType.REFRESH_TOKEN ? refreshTokenSecret : accessTokenSecret
                ));

        return ComPToken.of(tokenType, token);
    }

    public Optional<Passport> validateToken(String token, TokenType tokenType) {
        try {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(
                    tokenType == TokenType.REFRESH_TOKEN ? refreshTokenSecret : accessTokenSecret
            )).build().verify(token);

            return Optional.ofNullable(Passport.of(decodedJWT.getSubject(), decodedJWT.getClaim("rol").asString()));
        } catch (JWTVerificationException e) {
            return Optional.empty();
        }
    }

}
