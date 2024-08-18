package com.example.myplant.adapter.in.web.response;

import com.example.myplant.domain.MyPlant;

import java.time.LocalDate;

public record MyPlantDetailResponse(
        Long myPlantId,
        String plantName,
        LocalDate plantBirthday,
        Long plantCharacterId,
        Integer relationshipScore
) {
    public static MyPlantDetailResponse from(MyPlant myPlant) {
        return new MyPlantDetailResponse(
                myPlant.getMyPlantId(),
                myPlant.getPlantName(),
                myPlant.getPlantBirthday(),
                myPlant.getPlantCharacterId(),
                myPlant.getRelationshipScore()
        );
    }
}
