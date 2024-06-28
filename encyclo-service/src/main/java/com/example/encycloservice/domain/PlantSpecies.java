package com.example.encycloservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class PlantSpecies {

    private Long id;
    private PlantTaxonomy plantTaxonomy;
    // Todo : 유니크 값으로 만들어서 식물 이름으로도 조회받을 수 있도록 하면 좋을 것 같음
    private String commonName;
    private String description;
    private PlantingInfo wateringInfo;
    private PlantingInfo environmentInfo;
    private PlantingInfo humidityInfo;
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
                .humidityInfo(plantSpeciesCreate.humidityInfo())
                .humidCondition(plantSpeciesCreate.humidCondition())
                .temperatureCondition(plantSpeciesCreate.temperatureCondition())
                .humidCondition(plantSpeciesCreate.humidCondition())
                .imageUrls(plantSpeciesCreate.imageUrls())
                .build();
    }

}
