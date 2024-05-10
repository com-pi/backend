package com.example.authserver.domain;

import lombok.Getter;

import java.time.Instant;
import java.util.function.Supplier;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MINUTES;

@Getter
public enum TokenType {
    ACCESS_TOKEN(
            "엑세스 토큰이 유효하지 않습니다.",
            () -> Instant.now().plus(20, MINUTES),
            1200 // 20 * 60
    ),

    REFRESH_TOKEN(
            "리프레시 토큰이 유효하지 않습니다.",
            () -> Instant.now().plus(14, DAYS),
            1209600 // 14 * 24 * 60 * 60
    );

    private final String invalidMessage;
    private final Supplier<Instant> instant;
    private final long Seconds;


    TokenType(String invalidMessage, Supplier<Instant> instant, long toSeconds) {
        this.invalidMessage = invalidMessage;
        this.instant = instant;
        this.Seconds = toSeconds;
    }

    public Instant getInstant() {
        return instant.get();
    }

}