package com.example.encycloservice.domain;

import lombok.Builder;

import java.util.List;

@Builder
public record PlantSpeciesCreate(
        PlantTaxonomy plantTaxonomy,
        String commonName,
        String description,
        PlantingInfo wateringInfo,
        PlantingInfo environmentInfo,
        PlantingInfo humidityInfo,
        PlantingCondition temperatureCondition,
        PlantingCondition humidCondition,
        List<String> imageUrls
) {
}
