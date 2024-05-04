package com.example.authserver.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.authserver.domain.ComPToken;
import com.example.authserver.domain.Member;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.function.Function;

@Component
@Getter
public class JwtGenerator {

    private static final String TOKEN_ISSUER = "꼼삐";
    @Value("${secret.accessToken}")
    private String accessTokenSecret;
    @Value("${secret.refreshToken}")
    private String refreshTokenSecret;


    public ComPToken generateToken(Member member, TokenType tokenType) {
        String token = JWT.create().withIssuer(TOKEN_ISSUER)
                .withExpiresAt(Instant.now().plus(20, ChronoUnit.MINUTES))
                .withSubject(member.getId().toString())
                .sign(Algorithm.HMAC256(tokenType.getSecret(this)));
        return ComPToken.of(token);
    }

    public enum TokenType {
        ACCESS_TOKEN(jwtGenerator -> jwtGenerator.accessTokenSecret),
        REFRESH_TOKEN(jwtGenerator -> jwtGenerator.refreshTokenSecret);

        private final Function<JwtGenerator, String> getSecret;

        TokenType(Function<JwtGenerator, String> getSecret) {
            this.getSecret = getSecret;
        }

        public byte[] getSecret(JwtGenerator jwtGenerator){
            return getSecret.apply(jwtGenerator).getBytes(StandardCharsets.UTF_8);
        }
    }

}
