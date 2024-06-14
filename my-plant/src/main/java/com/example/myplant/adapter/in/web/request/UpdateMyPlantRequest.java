package com.example.myplant.adapter.in.web.request;

import com.example.myplant.domain.MyPlant;
import com.example.myplant.domain.PlantCharacter;

import java.time.LocalDate;

public record UpdateMyPlantRequest(
        Long myPlantId,
        Long characterId,
        Long memberId,
        LocalDate lastWaterday,
        LocalDate plantBirthday,
        String plantType,
        String plantLocation,
        String plantName,
        String potType,
        Integer wateringIntervalInDays
) {
    public MyPlant toDomain(Long memberId) {
        return MyPlant.builder()
                .myPlantId(myPlantId)
                .memberId(memberId)
                .plantCharacter(PlantCharacter.ofId(this.characterId))
                .lastWateringDate(this.lastWaterday)
                .plantBirthday(this.plantBirthday)
                .plantType(this.plantType)
                .plantSpot(this.plantLocation)
                .plantName(this.plantName)
                .potType(this.potType)
                .wateringIntervalInDays(wateringIntervalInDays)
                .build();
    }
}