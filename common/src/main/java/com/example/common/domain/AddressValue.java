package com.example.common.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AddressValue(
        @NotNull @NotBlank @Size(max = 30) String sido,
        @NotNull @NotBlank @Size(max = 30) String sigungu,
        @NotNull @NotBlank @Size(max = 30) String eupmyundong
) {
}
