package com.example.encycloservice;

import com.example.encycloservice.adapter.out.persistence.entity.PlantSpeciesEntity;
import com.example.encycloservice.domain.SearchPlantQueryResult;
import org.springframework.stereotype.Component;

@Component
public class DomainMapper {

    public static SearchPlantQueryResult.SingleSearchPlantResult toDomain(PlantSpeciesEntity entity) {
        return SearchPlantQueryResult.SingleSearchPlantResult.builder()
                .plantSpeciesId(entity.getId())
                .commonName(entity.getCommonName())
                .imageUrl(entity.getImageUrls().getFirst())
                .build();
    }

}
