package com.example.myplant.adapter.out.persistence.entity;

import com.example.common.baseentity.DeletedAtAbstractEntity;
import com.example.myplant.domain.MyPlant;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "my_plant")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyPlantEntity extends DeletedAtAbstractEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long myPlantId;

    private Long memberId;

    private String plantName;

    private Long plantTypeId;

    private String plantType;

    private LocalDate plantBirthday;

    private LocalDate lastWateringDate;

    private Integer wateringIntervalInDays;

    private String plantSpot;

    private String potType;

    private Integer relationshipScore;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plant_character_id")
    private PlantCharacterEntity plantCharacterEntity;

    /**
     * 생성자
     */
    @Builder
    public MyPlantEntity(Long myPlantId, Long memberId, String plantName, Long plantTypeId, String plantType, LocalDate plantBirthday, LocalDate lastWateringDate, Integer wateringIntervalInDays, String plantSpot, String potType, Integer relationshipScore, PlantCharacterEntity plantCharacterEntity) {
        this.myPlantId = myPlantId;
        this.memberId = memberId;
        this.plantName = plantName;
        this.plantTypeId = plantTypeId;
        this.plantType = plantType;
        this.plantBirthday = plantBirthday;
        this.lastWateringDate = lastWateringDate;
        this.wateringIntervalInDays = wateringIntervalInDays;
        this.plantSpot = plantSpot;
        this.potType = potType;
        this.relationshipScore = relationshipScore;
        this.plantCharacterEntity = plantCharacterEntity;
    }

    /**
     *
     */
    public MyPlant toDomain() {
        return MyPlant.builder()
                .myPlantId(this.myPlantId)
                .memberId(this.memberId)
                .plantName(this.plantName)
                .plantTypeId(this.plantTypeId)
                .plantType(this.plantType)
                .plantBirthday(this.plantBirthday)
                .lastWateringDate(this.lastWateringDate)
                .wateringIntervalInDays(this.wateringIntervalInDays)
                .plantSpot(this.plantSpot)
                .potType(this.potType)
                .relationshipScore(this.relationshipScore)
                .plantCharacterId(this.plantCharacterEntity.toDomain().getPlantCharacterId())
                .build();
    }

    public static MyPlantEntity fromDomain(MyPlant myPlant) {
        return MyPlantEntity.builder()
                .myPlantId(myPlant.getMyPlantId())
                .memberId(myPlant.getMemberId())
                .plantName(myPlant.getPlantName())
                .plantType(myPlant.getPlantType())
                .plantBirthday(myPlant.getPlantBirthday())
                .lastWateringDate(myPlant.getLastWateringDate())
                .wateringIntervalInDays(myPlant.getWateringIntervalInDays())
                .plantSpot(myPlant.getPlantSpot())
                .potType(myPlant.getPotType())
                .relationshipScore(myPlant.getRelationshipScore())
                .plantCharacterEntity(PlantCharacterEntity.ofId(myPlant.getPlantCharacterId()))
                .build();
    }

    public void update(MyPlant myPlant) {
        this.plantName = myPlant.getPlantName();
        this.plantTypeId = myPlant.getPlantTypeId();
        this.plantType = myPlant.getPlantType();
        this.plantBirthday = myPlant.getPlantBirthday();
        this.lastWateringDate = myPlant.getLastWateringDate();
        this.wateringIntervalInDays = myPlant.getWateringIntervalInDays();
        this.plantSpot = myPlant.getPlantSpot();
        this.potType = myPlant.getPotType();
    }

    public void updatePlantCharacter(PlantCharacterEntity plantCharacterEntity) {
        this.plantCharacterEntity = plantCharacterEntity;
    }

    public void updatePlantSpot(String plantSpot) {
        this.plantSpot = plantSpot;
    }

    @Override
    public void delete() {
        super.delete();
    }
}
