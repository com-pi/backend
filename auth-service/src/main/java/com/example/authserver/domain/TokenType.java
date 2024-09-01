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
            () -> Instant.now().plus(20, MINUTES),
            1200, // 20 * 60
            JwtUtilImpl::getAccessTokenSecret
    ),

    REFRESH_TOKEN(
            () -> Instant.now().plus(14, DAYS),
            1209600, // 14 * 24 * 60 * 60
            JwtUtilImpl::getRefreshTokenSecret
    ),

    PASSWORD_CHANGE_TOKEN(
            () -> Instant.now().plus(1, DAYS),
            86400,
            JwtUtilImpl::getPasswordChangeTokenSecret
    );

    private final Supplier<Instant> instant;
    @Getter
    private final int Seconds;
    private final Function<JwtUtilImpl, String> secret;


    TokenType(Supplier<Instant> instant, int toSeconds, Function<JwtUtilImpl, String> secret) {
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