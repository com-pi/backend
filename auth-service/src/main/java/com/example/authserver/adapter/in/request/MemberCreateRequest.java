package com.example.authserver.adapter.in.request;


import com.example.authserver.domain.MemberCreate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.UUID;

@Builder
public record MemberCreateRequest(
        @Email String email,
        @Size(min = 9, message = "비밀번호는 9자 이상이어야 합니다.") String password,
        @Size(min = 10, max = 11, message = "올바른 휴대전화 번호를 입력해주세요")String phoneNumber
) {
    public MemberCreate toDomain() {
        return MemberCreate.builder()
                .email(email)
                .password(password)
                .phoneNumber(phoneNumber)
                .nickname(String.format("새회원_%s", UUID.randomUUID().toString().replace("-", "").substring(0, 8)))
                .build();
    }

}
