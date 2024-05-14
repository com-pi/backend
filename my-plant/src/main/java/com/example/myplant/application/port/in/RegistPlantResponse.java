package com.example.myplant.application.port.in;

import com.example.myplant.domain.Plant;
import com.example.myplant.domain.PlantLocation;
import com.example.myplant.domain.PlantStatus;

import java.util.List;

public record RegistPlantResponse (
        Long id,
        Long memberId,
        String plantName,
        String plantType,
        String plantAge,
        String plantBirthday,
        List<String> plantImageUrl,
        String plantWaterDays,
        String lastWaterDay,
        String intimacy,
        String plantDescription,
        PlantLocation plantLocation,
        PlantStatus plantStatus
) {
    public static RegistPlantResponse fromEntity(Plant plant) {
        return new RegistPlantResponse(
                plant.getId(),
                plant.getMemberId(),
                plant.getPlantName(),
                plant.getPlantType(),
                plant.getPlantAge(),
                plant.getPlantBirthday(),
                plant.getPlantImageUrl(),
                plant.getPlantWaterDays(),
                plant.getLastWaterDay(),
                plant.getPlantAge(),
                plant.getPlantDescription(),
                plant.getPlantLocation(),
                plant.getPlantStatus()
        );
    }

}

