package com.example.authserver.adapter.in.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record JoinRequest(
        @Email String email,
        @Size(min = 9, message = "비밀번호는 9자 이상이어야 합니다.") String password,
        @Size(min = 10, max = 11, message = "올바른 휴대전화 번호를 입력해주세요")String phoneNumber
) {}
