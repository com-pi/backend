package com.example.myplant.adapter.in.web.response;

import com.example.myplant.domain.MyPlant;

import java.time.LocalDate;

public record MyPlantForUpdateResponse(
        Long characterId,
        LocalDate lastWaterday,
        LocalDate plantBirthday,
        String plantType,
        String plantLocation,
        String plantName,
        String potType,
        Integer wateringIntervalInDays
) {
    public static MyPlantForUpdateResponse from(MyPlant myPlant) {
        return new MyPlantForUpdateResponse(
                myPlant.getPlantCharacterId(),
                myPlant.getLastWateringDate(),
                myPlant.getPlantBirthday(),
                myPlant.getPlantType(),
                myPlant.getPlantSpot(),
                myPlant.getPlantName(),
                myPlant.getPotType(),
                myPlant.getWateringIntervalInDays()
        );
    }
}
