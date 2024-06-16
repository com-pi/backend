package com.example.encycloservice.adapter.in.response;

import com.example.encycloservice.domain.PlantSpecies;
import com.example.encycloservice.domain.PlantTaxonomy;
import com.example.encycloservice.domain.PlantingCondition;
import com.example.encycloservice.domain.PlantingInfo;
import lombok.Builder;

import java.util.List;

@Builder
public record PlantSpeciesDetailResponse(
        String commonName,
        String description,
        PlantTaxonomy plantTaxonomy,
        PlantingInfo wateringInfo,
        PlantingInfo environmentInfo,
        PlantingInfo humidityInfo,
        PlantingCondition temperatureCondition,
        PlantingCondition humidCondition,
        List<String> imageUrls
){

    public static PlantSpeciesDetailResponse toResponse(PlantSpecies plantSpecies){
        return PlantSpeciesDetailResponse.builder()
                .commonName(plantSpecies.getCommonName())
                .description(plantSpecies.getDescription())
                .wateringInfo(plantSpecies.getWateringInfo())
                .environmentInfo(plantSpecies.getEnvironmentInfo())
                .humidityInfo(plantSpecies.getHumidityInfo())
                .temperatureCondition(plantSpecies.getTemperatureCondition())
                .humidCondition(plantSpecies.getHumidCondition())
                .imageUrls(plantSpecies.getImageUrls())
                .build();
    }

}
