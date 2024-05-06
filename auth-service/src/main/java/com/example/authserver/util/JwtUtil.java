package com.example.authserver.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.authserver.domain.ComPToken;
import com.example.authserver.domain.Member;
import com.example.common.domain.Passport;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.function.Function;

@Component
@Getter
public class JwtUtil {

    private static final String TOKEN_ISSUER = "꼼삐";
    @Value("${secret.accessToken}")
    private String accessTokenSecret;
    @Value("${secret.refreshToken}")
    private String refreshTokenSecret;


    public ComPToken generateToken(Member member, TokenType tokenType) {

        String token = JWT.create().withIssuer(TOKEN_ISSUER)
                .withExpiresAt(Instant.now().plus(20, ChronoUnit.MINUTES))
                .withSubject(member.getId().toString())
                .withClaim("rol", member.getRole().name())
                .sign(Algorithm.HMAC256(tokenType.getSecret(this)));

        return ComPToken.of(token);
    }

    public ComPToken generateToken(Passport passPort, TokenType tokenType) {

        String token = JWT.create().withIssuer(TOKEN_ISSUER)
                .withExpiresAt(Instant.now().plus(20, ChronoUnit.MINUTES))
                .withSubject(passPort.memberId().toString())
                .withClaim("rol", passPort.role().name())
                .sign(Algorithm.HMAC256(tokenType.getSecret(this)));

        return ComPToken.of(token);
    }

    public Optional<Passport> validateToken(String token, TokenType tokenType) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(tokenType.getSecret(this))).build().verify(token);
        if (decodedJWT.getExpiresAt().toInstant().isBefore(Instant.now())) {
            return Optional.ofNullable(Passport.of(decodedJWT.getSubject(), decodedJWT.getClaim("rol").asString()));
        }
        return Optional.empty();
    }

    @Getter
    public enum TokenType {
        ACCESS_TOKEN(
                jwtUtil -> jwtUtil.accessTokenSecret,
                "엑세스 토큰이 유효하지 않습니다."
                ),

        REFRESH_TOKEN(
                jwtUtil -> jwtUtil.refreshTokenSecret,
                "리프레시 토큰이 유효하지 않습니다."
                );

        private final Function<JwtUtil, String> getSecret;
        private final String invalidMessage;


        TokenType(Function<JwtUtil, String> getSecret, String invalidMessage) {
            this.getSecret = getSecret;
            this.invalidMessage = invalidMessage;
        }

        public byte[] getSecret(JwtUtil jwtUtil){
            return getSecret.apply(jwtUtil).getBytes(StandardCharsets.UTF_8);
        }
    }

}
