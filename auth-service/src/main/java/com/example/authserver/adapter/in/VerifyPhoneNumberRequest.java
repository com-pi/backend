package com.example.authserver.adapter.in;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record VerifyPhoneNumberRequest(
        @Email String email,
        @NotNull @NotBlank @Size(min = 10, max = 11) String phoneNumber
) {}
