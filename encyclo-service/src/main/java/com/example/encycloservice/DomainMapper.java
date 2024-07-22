package com.example.encycloservice;

import com.example.encycloservice.adapter.out.persistence.entity.PlantSpeciesEntity;
import com.example.encycloservice.domain.SearchPlantByKeywordResult;
import org.springframework.stereotype.Component;

@Component
public class DomainMapper {

    // Todo : 이 클래스 삭제
    public static SearchPlantByKeywordResult.SingleSearchPlantResult toDomain(PlantSpeciesEntity entity) {
        return SearchPlantByKeywordResult.SingleSearchPlantResult.builder()
                .plantSpeciesId(entity.getId())
                .commonName(entity.getCommonName())
                .imageUrl(entity.getImageUrls().get(0))
                .build();
    }

}
