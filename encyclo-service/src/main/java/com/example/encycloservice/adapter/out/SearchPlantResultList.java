package com.example.encycloservice.adapter.out;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;


public record SearchPlantResultList(List<SearchPlantResult> results) {

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record SearchPlantResult(
            String id,
            String name,
            String thumbnailUrl) {
    }

}
