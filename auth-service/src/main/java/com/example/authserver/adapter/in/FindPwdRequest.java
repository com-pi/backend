package com.example.authserver.adapter.in;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FindPwdRequest(
        @NotNull @NotBlank String phoneNumber,
        @NotNull @Email String email
) {
}
