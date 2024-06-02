package com.example.encycloservice.adapter.out;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

public record PlantDetailResult(String name, String description, String scientificName,
                                @JsonAlias("info") List<PlantingInfo> plantingInfos,
                                List<String> imageUrls) {
    public record PlantingInfo(
            String name,
            String info1,
            String info2
    ) {
    }
}
