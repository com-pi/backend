package com.example.authserver.adapter.in;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ModifyInfoRequest(
        @Size(max = 24)
        @Nonnull @NotBlank String nickname,
        @Size(max = 3000)
        @Nonnull @NotBlank String introduction
) {
}
