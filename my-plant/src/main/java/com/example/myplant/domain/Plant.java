package com.example.myplant.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Random;

@Entity
@Table(name = "plant")
@Getter // Getter만 생성하여 불변성을 유지
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자의 접근 레벨을 PROTECTED로 설정하여 직접 인스턴스화를 제한
@AllArgsConstructor(access = AccessLevel.PRIVATE) // Builder를 통해서만 인스턴스를 생성하도록 설정
public class Plant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Long plantId;

    private Long memberId;
    private String plantName;
    private String plantType;
    private int plantAge;
    private LocalDate plantBirthday;
    private LocalDate lastWaterday;
    private String plantDescription;

    @Embedded
    private WateringInfo wateringInfo;

    @Embedded
    private MaintenanceSchedule maintenanceSchedule;

    private String plantLocation;
    private String potType;
    private int intimacy;

    @ManyToOne
    @JoinColumn(name ="character_id")
    private Character character;


    @Builder
    public Plant(Long memberId, String plantName, String plantType, int plantAge, LocalDate plantBirthday,
                 LocalDate lastWaterday, WateringInfo wateringInfo, MaintenanceSchedule maintenanceSchedule,
                 String plantLocation, String potType, int intimacy, Character character) {
        this.memberId = memberId;
        this.plantName = plantName;
        this.plantType = plantType;
        this.plantAge = plantAge;
        this.plantBirthday = plantBirthday;
        this.lastWaterday = lastWaterday;
        this.wateringInfo = wateringInfo;
        this.maintenanceSchedule = maintenanceSchedule;
        this.plantLocation = plantLocation;
        this.potType = potType;
        this.intimacy = intimacy;
        this.character = character;
    }

    @PrePersist
    public void prePersist() {
        Random random = new Random();
        this.plantId = random.nextLong();
    }

    public void updatePlant(String plantName, String plantType, int plantAge, LocalDate plantBirthday,
                            LocalDate lastWaterday, WateringInfo wateringInfo, MaintenanceSchedule maintenanceSchedule,
                            String plantLocation, String potType, Character character) {
        this.plantName = plantName;
        this.plantType = plantType;
        this.plantAge = plantAge;
        this.plantBirthday = plantBirthday;
        this.lastWaterday = lastWaterday;
        this.wateringInfo = wateringInfo;
        this.maintenanceSchedule = maintenanceSchedule;
        this.plantLocation = plantLocation;
        this.potType = potType;
        this.character = character;
    }
}


