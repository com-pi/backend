package com.example.encycloservice.adapter.out.persistence.entity;

import com.example.common.baseentity.BaseTimeAbstractEntity;
import com.example.common.converter.JsonToStringListConverter;
import com.example.encycloservice.domain.*;
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
    @AttributeOverrides({
            @AttributeOverride(name = "species", column = @Column(name = "taxonomy_species")),
            @AttributeOverride(name = "genus", column = @Column(name = "taxonomy_genus")),
            @AttributeOverride(name = "family", column = @Column(name = "taxonomy_family"))
    })
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
            @AttributeOverride(name = "summary", column = @Column(name = "humidity_summary")),
            @AttributeOverride(name = "description", column = @Column(name = "humidity_description"))
    })
    private PlantingInfo humidityInfo;

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
                .plantTaxonomy(plantTaxonomy)
                .commonName(commonName)
                .description(description)
                .wateringInfo(wateringInfo)
                .environmentInfo(environmentInfo)
                .humidityInfo(humidityInfo)
                .temperatureCondition(temperatureCondition)
                .humidCondition(humidCondition)
                .imageUrls(imageUrls)
                .build();
    }

    public static PlantSpeciesEntity fromDomain(PlantSpecies plantSpecies){
        return PlantSpeciesEntity.builder()
                .id(plantSpecies.getId())
                .plantTaxonomy(plantSpecies.getPlantTaxonomy())
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

    public PlantBrief toBrief(){
        return PlantBrief.builder()
                .plantSpeciesId(id)
                .commonName(commonName)
                .scientificName(String.format("%s %s", plantTaxonomy.getGenus(), plantTaxonomy.getSpecies()))
                .imageUrl(imageUrls.get(0))
                .build();
    }

}
