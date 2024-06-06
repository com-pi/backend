package com.example.encycloservice.adapter.out;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record PlantDetailResult(String name,
                                String description,
                                String scientificName,
                                @JsonAlias("info") List<PlantingInfo> plantingInfos,
                                @JsonAlias("planting_condition") List<PlantingCondition> plantingConditions,
                                List<String> imageUrls) {

    public record PlantingInfo(
             String name,
             String info1,
             String info2
    ) {}

    public record PlantingCondition(
            String condition,
            int min,
            int max
    ){}

}
