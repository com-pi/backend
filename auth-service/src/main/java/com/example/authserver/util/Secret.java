package com.example.authserver.util;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

@Getter
public class Secret {

    public static String accessTokenSecret;
    public static String refreshTokenSecret;

    @Value("${secret.accessToken}")
    private String secret1;
    @Value("${secret.refreshToken}")
    private String secret2;

    @PostConstruct
    public void init() {
        accessTokenSecret = secret1;
        refreshTokenSecret = secret2;
    }

}
