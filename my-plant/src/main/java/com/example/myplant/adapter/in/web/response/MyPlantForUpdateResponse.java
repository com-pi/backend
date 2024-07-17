package com.example.myplant.adapter.in.web.response;

import com.example.myplant.domain.MyPlant;

import java.time.LocalDate;

public record MyPlantForUpdateResponse(
        Long myPlantId,
        String plantName,
        LocalDate plantBirthday,
        String imageUrl,
        Integer relationshipScore
) {
    public static MyPlantDetailResponse from(MyPlant myPlant) {
        return new MyPlantDetailResponse(
                myPlant.getMyPlantId(),
                myPlant.getPlantName(),
                myPlant.getPlantBirthday(),
                myPlant.getPlantCharacter().getImageUrl(),
                myPlant.getRelationshipScore()
        );
    }
}
