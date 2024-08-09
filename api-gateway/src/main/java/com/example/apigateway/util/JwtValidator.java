package com.example.apigateway.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.apigateway.domain.Passport;
import com.example.apigateway.exception.InvalidTokenException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtValidator {

    @Value("${secret.accessToken}")
    private String accessTokenSecret;

    public Passport validateToken(String token) {
        try {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(accessTokenSecret)).build().verify(token);
            String memberId = decodedJWT.getSubject();
            String role = decodedJWT.getClaim("rol").asString();
            return Passport.of(memberId, role);
        } catch(TokenExpiredException e) {
            throw new InvalidTokenException("토큰이 만료 되었습니다.", e);
        } catch (JWTVerificationException e) {
            throw new InvalidTokenException("엑세스 토큰이 유효하지 않습니다.", e);
        }

    }

}
