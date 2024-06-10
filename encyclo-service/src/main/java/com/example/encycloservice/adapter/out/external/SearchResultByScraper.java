package com.example.encycloservice.adapter.out.external;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;

import java.util.List;

@Builder
public record SearchResultByScraper(
        List<SearchPlantResult> results
) {

    @Builder
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record SearchPlantResult(
            String id,
            String name,
            String thumbnailUrl) {
    }

}
