package com.example.encycloservice.adapter.out.persistence;

import com.example.common.baseentity.BaseTimeAbstractEntity;
import com.example.common.converter.JsonToStringListConverter;
import com.example.encycloservice.domain.PlantSpecies;
import com.example.encycloservice.domain.PlantTaxonomy;
import com.example.encycloservice.domain.PlantingCondition;
import com.example.encycloservice.domain.PlantingInfo;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Entity
@Table(name = "PLANT_SPECIES")
public class PlantSpeciesEntity extends BaseTimeAbstractEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private PlantTaxonomy plantTaxonomy;

    @Column(unique = true)
    private String commonName;

    @Lob
    private String description;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "summary", column = @Column(name = "watering_summary")),
            @AttributeOverride(name = "description", column = @Column(name = "watering_description"))
    })
    private PlantingInfo wateringInfo;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "summary", column = @Column(name = "environment_summary")),
            @AttributeOverride(name = "description", column = @Column(name = "environment_description"))
    })
    private PlantingInfo environmentInfo;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "min", column = @Column(name = "temperature_min")),
            @AttributeOverride(name = "max", column = @Column(name = "temperature_max"))
    })
    private PlantingCondition temperatureCondition;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "min", column = @Column(name = "humid_min")),
            @AttributeOverride(name = "max", column = @Column(name = "humid_max"))
    })
    private PlantingCondition humidCondition;

    @Lob
    @Convert(converter = JsonToStringListConverter.class)
    private List<String> imageUrls;

    public PlantSpecies toDomain(){
        return PlantSpecies.builder()
                .id(id)
                .commonName(commonName)
                .description(description)
                .wateringInfo(wateringInfo)
                .environmentInfo(environmentInfo)
                .temperatureCondition(temperatureCondition)
                .humidCondition(humidCondition)
                .imageUrls(imageUrls)
                .build();
    }

    public static PlantSpeciesEntity fromDomain(PlantSpecies plantSpecies){
        return PlantSpeciesEntity.builder()
                .id(plantSpecies.getId())
                .commonName(plantSpecies.getCommonName())
                .description(plantSpecies.getDescription())
                .wateringInfo(plantSpecies.getWateringInfo())
                .environmentInfo(plantSpecies.getEnvironmentInfo())
                .temperatureCondition(plantSpecies.getTemperatureCondition())
                .humidCondition(plantSpecies.getHumidCondition())
                .imageUrls(plantSpecies.getImageUrls())
                .build();
    }

}
