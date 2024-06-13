package com.example.myplant.adapter.out.persistence.entity;

import com.example.myplant.domain.MyPlant;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "my_plant")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyPlantEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long myPlantId;

    private Long memberId;

    private String plantName;

    private String plantType;

    private LocalDate plantBirthday;

    private LocalDate lastWateringDate;

    private String plantDescription;

    private Integer wateringIntervalInDays;

    private String plantSpot;

    private String potType;

    private Long characterId;

    /**
     * 생성자
     */
    @Builder
    public MyPlantEntity(Long myPlantId, Long memberId, String plantName, String plantType, LocalDate plantBirthday, LocalDate lastWateringDate, String plantDescription, Integer wateringIntervalInDays, String plantSpot, String potType, Long characterId) {
        this.myPlantId = myPlantId;
        this.memberId = memberId;
        this.plantName = plantName;
        this.plantType = plantType;
        this.plantBirthday = plantBirthday;
        this.lastWateringDate = lastWateringDate;
        this.plantDescription = plantDescription;
        this.wateringIntervalInDays = wateringIntervalInDays;
        this.plantSpot = plantSpot;
        this.potType = potType;
        this.characterId = characterId;
    }

    /**
     *
     */
    public MyPlant toDomain() {
        return MyPlant.builder()
                .myPlantId(this.myPlantId)
                .memberId(this.memberId)
                .plantName(this.plantName)
                .plantType(this.plantType)
                .plantBirthday(this.plantBirthday)
                .lastWateringDate(this.lastWateringDate)
                .plantDescription(this.plantDescription)
                .wateringIntervalInDays(this.wateringIntervalInDays)
                .plantSpot(this.plantSpot)
                .potType(this.potType)
                .characterId(this.characterId)
                .build();
    }

    public static MyPlantEntity fromDomain(MyPlant myPlant) {
        return MyPlantEntity.builder()
                .memberId(myPlant.getMemberId())
                .plantName(myPlant.getPlantName())
                .plantType(myPlant.getPlantType())
                .plantBirthday(myPlant.getPlantBirthday())
                .lastWateringDate(myPlant.getLastWateringDate())
                .plantDescription(myPlant.getPlantDescription())
                .wateringIntervalInDays(myPlant.getWateringIntervalInDays())
                .plantSpot(myPlant.getPlantSpot())
                .potType(myPlant.getPotType())
                .characterId(myPlant.getCharacterId())
                .build();
    }

}
