package com.example.authserver.application.port.out.external;


import com.example.authserver.domain.MemberCreate;

import java.util.UUID;

public record NaverUserInfoResponse(
        String resultcode,
        String message,
        NaverProfile response) {

    public record NaverProfile(
            String id,
            String nickname,
            String email,
            String gender,
            String age,
            String birthday,
            String profile_image,
            String birthyear,
            String mobile
    ) {
    }

    public MemberCreate toDomain() {
        return MemberCreate.builder()
                .naverId(response.id)
                .nickname(response.nickname() != null ?
                        response.nickname() : "새회원" +
                        UUID.randomUUID().toString().replace("-", "")
                )
                .email(response.email)
                .imageUrl(response.profile_image)
                .phoneNumber(response.mobile)
                .build();
    }

}
