package com.example.authserver.adapter.in;

import io.swagger.v3.oas.annotations.Parameter;

public record VerifyPhoneNumberRequest(
        @Parameter(description = "이메일 주소") String email,
        @Parameter(description = "핸드폰 번호") String phoneNumber
) {}
