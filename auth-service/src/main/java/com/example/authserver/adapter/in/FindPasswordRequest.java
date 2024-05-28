package com.example.authserver.adapter.in;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FindPasswordRequest(
        @Parameter(description = "이메일")
        @NotNull @NotBlank @Email String email,
        @Parameter(description = "핸드폰 번호")
        @NotNull @NotBlank String phoneNumber
) {
}