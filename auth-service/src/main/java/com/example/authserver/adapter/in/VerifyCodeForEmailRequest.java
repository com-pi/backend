package com.example.authserver.adapter.in;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record
VerifyCodeForEmailRequest(
        @NotNull @NotBlank String phoneNumber,
        @NotNull @NotBlank String verifyCode
) {
}
