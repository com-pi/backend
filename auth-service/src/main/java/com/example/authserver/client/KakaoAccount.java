package com.example.authserver.client;


public record KakaoAccount(
        String email,
        KakaoProfile kakao_profile
) {}
