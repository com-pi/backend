package com.example.authserver.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class JWT {

    private final String token;

    public static JWT of(String token) {
        return new JWT(token);
    }

}
