package com.example.authserver.adapter.in;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VerifyCodeForJoinRequest(
        @Parameter(name = "email", description = "이메일 주소") String email,
        @Parameter(name = "phoneNumber", description = "핸드폰 번호") String phoneNumber,
        @Parameter(name = "code", description = "인증코드") @NotNull @NotBlank String verificationCode
) {
}
