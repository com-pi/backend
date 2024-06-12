package com.example.authserver.domain;

import com.example.authserver.util.JwtUtilImpl;
import lombok.Getter;

import java.time.Instant;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MINUTES;

public enum TokenType {
    ACCESS_TOKEN(
            "엑세스 토큰이 유효하지 않습니다.",
            () -> Instant.now().plus(20, MINUTES),
            1200, // 20 * 60
            JwtUtilImpl::getAccessTokenSecret
    ),

    REFRESH_TOKEN(
            "리프레시 토큰이 유효하지 않습니다.",
            () -> Instant.now().plus(14, DAYS),
            1209600, // 14 * 24 * 60 * 60
            JwtUtilImpl::getRefreshTokenSecret
    ),

    PASSWORD_CHANGE_TOKEN(
            "비밀번호를 변경을 위한 토큰이 유효하지 않습니다.",
            () -> Instant.now().plus(1, DAYS),
            86400,
            JwtUtilImpl::getPasswordChangeTokenSecret
    );

    @Getter
    private final String invalidMessage;
    private final Supplier<Instant> instant;
    @Getter
    private final long Seconds;
    private final Function<JwtUtilImpl, String> secret;


    TokenType(String invalidMessage, Supplier<Instant> instant, long toSeconds, Function<JwtUtilImpl, String> secret) {
        this.invalidMessage = invalidMessage;
        this.instant = instant;
        this.Seconds = toSeconds;
        this.secret = secret;
    }

    public Instant getInstant() {
        return instant.get();
    }

    public String getSecret(JwtUtilImpl jwtUtil) {
        return secret.apply(jwtUtil);
    }

}