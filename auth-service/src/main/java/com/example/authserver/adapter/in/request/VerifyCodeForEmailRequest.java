package com.example.authserver.adapter.in.request;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record
VerifyCodeForEmailRequest(
        @Parameter(description = "핸드폰 번호")
        @NotNull @NotBlank String phoneNumber,
        @Parameter(description = "인증 코드")
        @NotNull @NotBlank String verifyCode
) {
}
