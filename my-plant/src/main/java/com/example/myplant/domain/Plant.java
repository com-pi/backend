package com.example.myplant.domain;

import jakarta.persistence.*;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;



@Entity
@Data
@NoArgsConstructor
public class Plant {    // 식물 정보 엔티티

    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;
    private String plantName;
    private String plantType;
    private int plantAge;
    private LocalDate plantBirthday;

    @ElementCollection  // plantImageUrls 여러 값 가질 수 있게
    private List<String> plantImageUrls;

    @Embedded
    private WateringInfo wateringInfo;

    @Embedded
    private MaintenanceSchedule maintenanceSchedule;

    private String plantLocation; // 식물 위치
    private String potType; // 화분 타입

    @Builder
    public Plant(Long memberId, String plantName, String plantType, int plantAge, LocalDate plantBirthday, List<String> plantImageUrls, WateringInfo wateringInfo, MaintenanceSchedule maintenanceSchedule, String plantLocation, String potType) {
        this.memberId = memberId;
        this.plantName = plantName;
        this.plantType = plantType;
        this.plantAge = plantAge;
        this.plantBirthday = plantBirthday;
        this.plantImageUrls = plantImageUrls;
        this.wateringInfo = wateringInfo;
        this.maintenanceSchedule = maintenanceSchedule;
        this.plantLocation = plantLocation;
        this.potType = potType;
    }
}