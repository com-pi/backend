package com.example.apigateway.domain;

import lombok.Getter;

import java.time.Instant;
import java.util.function.Supplier;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MINUTES;

@Getter
public enum TokenType {
    ACCESS_TOKEN(
            () -> Instant.now().plus(20, MINUTES),
            1200 // 20 * 60
    ),

    REFRESH_TOKEN(
            () -> Instant.now().plus(14, DAYS),
            1209600 // 14 * 24 * 60 * 60
    );

    private final Supplier<Instant> instant;
    private final long Seconds;


    TokenType(Supplier<Instant> instant, long toSeconds) {
        this.instant = instant;
        this.Seconds = toSeconds;
    }

}