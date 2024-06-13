package com.example.myplant.adapter.in.web.response;

import com.example.myplant.domain.MyPlant;

import java.time.LocalDate;

public record MyPlantResponse(
        Long myPlantId,
        String plantName,
        LocalDate plantBirthday,
        String characterIllustrationUrl
) {
    public static MyPlantResponse from(MyPlant myPlant) {
        return new MyPlantResponse(
                myPlant.getMyPlantId(),
                myPlant.getPlantName(),
                myPlant.getPlantBirthday(),
                myPlant.getCharacterId().toString()
        );
    }
}
