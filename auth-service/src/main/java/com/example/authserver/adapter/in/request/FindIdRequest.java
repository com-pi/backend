package com.example.authserver.adapter.in.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FindIdRequest(
        @NotNull @NotBlank String phoneNumber
) {
}
