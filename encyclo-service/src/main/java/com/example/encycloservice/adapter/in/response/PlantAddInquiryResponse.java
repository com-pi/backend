package com.example.encycloservice.adapter.in.response;

import com.example.encycloservice.domain.PlantAddInquiry;
import lombok.Builder;

import java.util.List;

@Builder
public record PlantAddInquiryResponse(
        long totalElement,
        int totalPage,
        List<PlantAddInquiry> plantAddInquiries
) {
}
