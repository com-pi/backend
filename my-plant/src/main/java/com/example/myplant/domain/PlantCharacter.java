package com.example.myplant.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
public class PlantCharacter {

    private Long plantCharacterId;

    private String imageUrl;

    private String characterName;

    /**
     *
     */
    public static PlantCharacter ofId(Long plantCharacterId) {
        return PlantCharacter.builder()
                .plantCharacterId(plantCharacterId)
                .build();
    }
}
