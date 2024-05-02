package com.example.authserver.application.port.out.external;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class KakaoUserInfoResponse {
    private final Long id;
    private final KakaoAccount kakao_account;

    /**
     * 카카오 사용자 정보를 받기 위한 레코드 입니다.
     * Feign 클라이언트의 맵핑을 위해 스네이크 케이스를 사용하였습니다.
     */
    public record KakaoAccount(
            String email,
            KakaoProfile kakao_profile
    ) {}

    public record KakaoProfile(
            String nickname,
            String thumbnail_image_url,
            String profile_image_url,
            Boolean is_default_image
    ){}

}