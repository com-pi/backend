package com.example.authserver.application.port.out.external;


import com.example.authserver.domain.MemberCreate;
import lombok.Getter;

@Getter
public class NaverUserInfoResponse {

    private final String resultcode;
    private final String message;
    private final NaverProfile response;

    public NaverUserInfoResponse(String resultcode, String message, NaverProfile response) {
        this.resultcode = resultcode;
        this.message = message;
        this.response = response;
    }

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
    ){}

    public MemberCreate toDomain() {
        return MemberCreate.builder()
                .naverId(response.id)
                .email(response.email)
                .imageUrl(response.profile_image)
                .phoneNumber(response.mobile)
                .build();
    }

}
