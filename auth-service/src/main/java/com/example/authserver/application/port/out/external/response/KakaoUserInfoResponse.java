package com.example.authserver.application.port.out.external.response;

import com.example.authserver.domain.MemberCreate;

import java.util.UUID;

public record KakaoUserInfoResponse(
        Long id,
        KakaoAccount kakao_account) {
    /**
     * 카카오 사용자 정보를 받기 위한 레코드 입니다.
     * Feign 클라이언트의 맵핑을 위해 스네이크 케이스를 사용하였습니다.
     */
    public record KakaoAccount(
            KakaoProfile profile,
            String email
    ) {
    }

    public record KakaoProfile(
            String nickname,
            String thumbnail_image_url,
            String profile_image_url,
            Boolean is_default_image
    ) {
    }

    public MemberCreate toMemberCreate() {
        return MemberCreate.builder()
                .kakaoId(id.toString())
                .email(kakao_account.email)
                .nickname(kakao_account.profile().nickname != null ?
                        kakao_account.profile().nickname : "새회원" +
                        UUID.randomUUID().toString().replace("-", "")
                )
                .imageUrl(kakao_account.profile().profile_image_url)
                .thumbnailUr(kakao_account.profile.thumbnail_image_url)
                .build();
    }

}