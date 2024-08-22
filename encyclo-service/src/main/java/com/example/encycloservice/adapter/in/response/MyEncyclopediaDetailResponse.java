package com.example.encycloservice.adapter.in.response;

import com.example.encycloservice.domain.MyEncyclopedia;
import com.example.encycloservice.domain.PlantBrief;
import lombok.Builder;

import java.util.List;

@Builder
public record MyEncyclopediaDetailResponse(
        long totalElement,
        int totalPage,
        String title,
        String coverImageUrl,
        List<PlantBrief> plantCollection
) {
}
