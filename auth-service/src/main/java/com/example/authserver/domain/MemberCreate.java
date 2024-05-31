package com.example.authserver.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class MemberCreate {
    private String kakaoId;
    private String naverId;
    private String phoneNumber;
    private String email;
    private String password;
    private String nickname;
    private String imageUrl;
    private String thumbnailUr;
}
