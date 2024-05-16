package com.example.authserver.adapter.in;

public record FindPwdRequest(
        String phoneNumber,
        String email
) {
}
