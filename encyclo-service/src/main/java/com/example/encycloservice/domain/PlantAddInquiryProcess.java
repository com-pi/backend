package com.example.encycloservice.domain;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Builder;

@Builder
public record PlantAddInquiryProcess(
        @Parameter(description = "식물 추가 요청 ID")
        PlantAddInquiry.Status status,
        String result
) {
}
