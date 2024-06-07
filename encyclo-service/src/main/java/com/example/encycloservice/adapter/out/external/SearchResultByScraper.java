package com.example.encycloservice.adapter.out.external;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;


public record SearchResultByScraper(
        List<SearchPlantResult> results
) {

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record SearchPlantResult(
            String id,
            String name,
            String thumbnailUrl) {
    }

}
