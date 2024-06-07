package com.example.encycloservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
@Builder
public class PlantSpecies {

    private Long id;
    private PlantTaxonomy plantTaxonomy;
    private String commonName;
    private String description;
    private PlantingInfo wateringInfo;
    private PlantingInfo environmentInfo;
    private PlantingCondition temperatureCondition;
    private PlantingCondition humidCondition;
    private List<String> imageUrls;

    public static PlantSpecies create(PlantSpeciesCreate plantSpeciesCreate) {
        return PlantSpecies.builder()
                .commonName(plantSpeciesCreate.commonName())
                .plantTaxonomy(plantSpeciesCreate.plantTaxonomy())
                .description(plantSpeciesCreate.description())
                .wateringInfo(plantSpeciesCreate.wateringInfo())
                .environmentInfo(plantSpeciesCreate.environmentInfo())
                .temperatureCondition(plantSpeciesCreate.temperatureCondition())
                .humidCondition(plantSpeciesCreate.humidCondition())
                .imageUrls(plantSpeciesCreate.imageUrls())
                .build();
    }

}
