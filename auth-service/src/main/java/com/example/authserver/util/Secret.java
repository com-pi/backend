package com.example.authserver.util;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Secret {

    public static String ACCESS_TOKEN_SECRET;
    public static String REFRESH_TOKEN_SECRET;
    public static String KAKAO_APP_KEY;
    public static String KAKAO_SECRET;
    public static String NAVER_APP_KEY;
    public static String NAVER_SECRET;

    @Value("${oauth.kakao.app-key}")
    private String kakaoAppKey;
    @Value("${oauth.kakao.secret}")
    private String kakaoSecret;
    @Value("${oauth.naver.app-key}")
    private String naverAppKey;
    @Value("${oauth.naver.secret}")
    private String naverSecret;
    @Value("${secret.accessToken}")
    private String accessTokenSecret;
    @Value("${secret.refreshToken}")
    private String refreshTokenSecret;

    @PostConstruct
    public void init() {
        ACCESS_TOKEN_SECRET = accessTokenSecret;
        REFRESH_TOKEN_SECRET = refreshTokenSecret;
        KAKAO_APP_KEY = kakaoAppKey;
        KAKAO_SECRET = kakaoSecret;
        NAVER_APP_KEY = naverAppKey;
        NAVER_SECRET = naverSecret;
    }

}
