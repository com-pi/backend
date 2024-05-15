package com.example.common.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddressValue(
        @NotNull @NotBlank String sido,
        @NotNull @NotBlank String sigungu,
        @NotNull @NotBlank String eupmyundong
) {
}
