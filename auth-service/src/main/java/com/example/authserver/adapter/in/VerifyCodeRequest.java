package com.example.authserver.adapter.in;

public record VerifyCodeRequest(
        String phoneNumber,
        String verifyCode
) {
}
