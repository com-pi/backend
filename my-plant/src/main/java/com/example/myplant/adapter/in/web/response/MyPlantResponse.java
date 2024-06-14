package com.example.myplant.adapter.in.web.response;

import com.example.myplant.domain.MyPlant;

import java.time.LocalDate;

public record MyPlantResponse(
        Long myPlantId,
        String plantName,
        LocalDate plantBirthday,
        String imageUrl
) {
    public static MyPlantResponse from(MyPlant myPlant) {
        return new MyPlantResponse(
                myPlant.getMyPlantId(),
                myPlant.getPlantName(),
                myPlant.getPlantBirthday(),
                myPlant.getPlantCharacter().getImageUrl()
        );
    }
}
