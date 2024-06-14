package com.example.myplant.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyPlant {

    private Long myPlantId;

    private Long memberId;

    private String plantName;

    private String plantType;

    private LocalDate plantBirthday;

    private LocalDate lastWateringDate;

    private Integer wateringIntervalInDays;

    private String plantSpot;

    private String potType;

    private Integer relationshipScore;

    private String deletionYn;

    private PlantCharacter plantCharacter;

}
