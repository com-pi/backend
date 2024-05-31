package com.example.authserver.adapter.in.request;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ChangePasswordRequest(
        @Parameter(description = "새로운 비밀번호")
        @NotNull @NotBlank @Size(min = 8, max = 15) String newPassword,
        @Parameter(description = "비밀번호 변경을 위한 토큰")
        @NotNull @NotBlank String changePasswordToken
) {
}
