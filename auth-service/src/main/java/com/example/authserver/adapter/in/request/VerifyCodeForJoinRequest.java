package com.example.authserver.adapter.in.request;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VerifyCodeForJoinRequest(
        @Parameter(description = "이메일 주소") String email,
        @Parameter(description = "핸드폰 번호") String phoneNumber,
        @Parameter(description = "인증코드") @NotNull @NotBlank String verificationCode
) {
}
