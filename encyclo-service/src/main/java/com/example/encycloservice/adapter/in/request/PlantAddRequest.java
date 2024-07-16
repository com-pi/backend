package com.example.encycloservice.adapter.in.request;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PlantAddRequest(

        @Parameter(description = "일반명")
        @Size(max = 255)
        @NotNull @NotBlank
        String commonName,

        @Parameter(description = "학명")
        @Size(max = 255)
        @NotNull @NotBlank
        String scientificName

) {
}
