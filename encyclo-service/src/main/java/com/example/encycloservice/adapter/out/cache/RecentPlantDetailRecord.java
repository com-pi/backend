package com.example.encycloservice.adapter.out.cache;

import com.example.encycloservice.domain.PlantSpecies;
import lombok.Builder;

@Builder
public record RecentPlantDetailRecord(
        String commonName,
        String imageUrl
) {
    public static RecentPlantDetailRecord fromDomain(PlantSpecies plantSpecies) {
        return RecentPlantDetailRecord.builder()
                .commonName(plantSpecies.getCommonName())
                .imageUrl(plantSpecies.getImageUrls().get(0))
                .build();
    }
}
