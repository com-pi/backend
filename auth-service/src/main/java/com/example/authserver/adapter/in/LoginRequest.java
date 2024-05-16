package com.example.authserver.adapter.in;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @Email String email,
        @Size(min = 9) String password
) {}
