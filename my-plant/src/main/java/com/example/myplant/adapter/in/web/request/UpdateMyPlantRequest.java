package com.example.myplant.adapter.in.web.request;

import com.example.myplant.domain.MyPlant;

import java.time.LocalDate;

public record UpdateMyPlantRequest(
        Long myPlantId,
        Long characterId,
        Long memberId,
        LocalDate lastWaterday,
        LocalDate plantBirthday,
        Long plantTypeId,
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
                .plantCharacterId(this.characterId)
                .lastWateringDate(this.lastWaterday)
                .plantBirthday(this.plantBirthday)
                .plantTypeId(this.plantTypeId)
                .plantType(this.plantType)
                .plantSpot(this.plantLocation)
                .plantName(this.plantName)
                .potType(this.potType)
                .wateringIntervalInDays(wateringIntervalInDays)
                .build();
    }
}
