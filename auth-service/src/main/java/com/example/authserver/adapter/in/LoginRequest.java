package com.example.authserver.adapter.in;

public record LoginRequest(
        String email,
        String password
) {}
