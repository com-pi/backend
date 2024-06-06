package com.example.encycloservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class PlantSpecies {

    private PlantTaxonomy plantTaxonomy;
    private String description;
    private PlantingInfo wateringInfo;
    private PlantingInfo environmentInfo;
    private PlantingInfo humidityInfo;
    private PlantingCondition temperatureCondition;
    private PlantingCondition humidCondition;

}
