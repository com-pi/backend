package com.example.encycloservice.domain;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(of = {"plantSpeciesId"})
public class PlantBrief {

    private Long plantSpeciesId;
    private String commonName;
    private String scientificName;
    private String imageUrl;

    public PlantBrief(Long plantSpeciesId) {
        this.plantSpeciesId = plantSpeciesId;
    }
}
