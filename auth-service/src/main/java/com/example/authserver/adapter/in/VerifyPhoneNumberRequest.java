package com.example.authserver.adapter.in;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record VerifyPhoneNumberRequest(
        @Email
        @Parameter(description = "이메일 주소") String email,
        @NotNull @NotBlank @Size(min = 10, max = 11)
        @Parameter(description = "핸드폰 번호") String phoneNumber
) {}
