package com.example.authserver.client;


public record KakaoProfile(
        String nickname,
        String thumbnail_image_url,
        String profile_image_url,
        Boolean is_default_image
) {}