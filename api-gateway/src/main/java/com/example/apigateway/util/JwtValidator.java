package com.example.apigateway.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.apigateway.domain.Passport;
import com.example.apigateway.exception.InvalidAccessTokenException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtValidator {

    @Value("${secret.accessToken}")
    private String accessTokenSecret;

    public Passport validateToken(String token) {
        try {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(accessTokenSecret)).build().verify(token);
            String subject = decodedJWT.getSubject();
            String memberId = decodedJWT.getClaim("rol").asString();
            String nickName = decodedJWT.getClaim("nik").asString();
            String thumbnail = decodedJWT.getClaim("img").asString();
            return Passport.of(subject, memberId, nickName, thumbnail);
        } catch (JWTVerificationException e) {
            throw new InvalidAccessTokenException("토큰 검증에 실패했습니다.", e);
        }

    }

}
