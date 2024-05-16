package com.example.authserver.adapter.in;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;

public record LoginRequest(
        @Email String email,
        @Min(9) String password
) {}
