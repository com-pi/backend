package com.example.encycloservice.adapter.out.external;

import com.example.encycloservice.domain.PlantSpeciesCreate;
import com.example.encycloservice.domain.PlantTaxonomy;
import com.example.encycloservice.domain.PlantingCondition;
import com.example.encycloservice.domain.PlantingInfo;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record PlantDetailResult(String name,
                                String description,
                                String scientificName,
                                @JsonAlias("info") List<PlantingInfo_> plantingInfos,
                                @JsonAlias("planting_condition") List<PlantingCondition_> plantingConditions,
                                List<String> imageUrls) {

    public record PlantingInfo_(
             String name,
             String info1,
             String info2
    ) {}

    public record PlantingCondition_(
            String condition,
            int min,
            int max
    ){}

    public PlantSpeciesCreate toDomainCreate(){
        String genus = scientificName.split(" ")[0];
        String species = scientificName.split(" ")[1];
        return PlantSpeciesCreate.builder()
                .plantTaxonomy(
                        PlantTaxonomy.builder()
                                .species(species)
                                .genus(genus)
                                .build())
                .description(description)
                .commonName(name)
                .wateringInfo(new PlantingInfo(plantingInfos.get(0).info1, plantingInfos.get(1).info2))
                .environmentInfo(new PlantingInfo(plantingInfos.get(1).info1, plantingInfos.get(1).info2))
                .temperatureCondition(new PlantingCondition(plantingConditions.get(0).min, plantingConditions.get(0).max))
                .humidCondition(new PlantingCondition(plantingConditions.get(1).min, plantingConditions.get(1).max))
                .imageUrls(imageUrls)
                .build();
    }

}
