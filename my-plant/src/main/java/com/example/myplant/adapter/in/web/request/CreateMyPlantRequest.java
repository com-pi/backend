package com.example.myplant.adapter.in.web.request;

import com.example.myplant.domain.MyPlant;

import java.time.LocalDate;

public record CreateMyPlantRequest (
        Long characterId,
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
                .memberId(memberId)
                .plantCharacterId(this.characterId)
                .lastWateringDate(this.lastWaterday)
                .plantBirthday(this.plantBirthday)
                .plantType(this.plantType)
                .plantSpot(this.plantLocation)
                .plantName(this.plantName)
                .potType(this.potType)
                .wateringIntervalInDays(wateringIntervalInDays)
                .relationshipScore(0)
                .build();
    }
}
