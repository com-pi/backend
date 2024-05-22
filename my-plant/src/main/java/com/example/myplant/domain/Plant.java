package com.example.myplant.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Entity
@Table(name = "plant")
@Getter // Getter만 생성하여 불변성을 유지
@Builder
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

    @PrePersist
    public void prePersiste() {
        Random random = new Random();
        this.plantId = (long) random.nextInt(999999); // 0부터 999999까지의 랜덤 숫자 생성
    }

    public void updatePlant(String plantName, String plantType, int plantAge, LocalDate plantBirthday,
                            LocalDate lastWaterday, WateringInfo wateringInfo,
                            MaintenanceSchedule maintenanceSchedule, String plantLocation, String potType) {
        if (plantName != null) this.plantName = plantName;
        if (plantType != null) this.plantType = plantType;
        if (plantAge != 0) this.plantAge = plantAge;
        if (plantBirthday != null) this.plantBirthday = plantBirthday;
        if (lastWaterday != null) this.lastWaterday = lastWaterday;
        if (wateringInfo != null) this.wateringInfo = wateringInfo;
        if (maintenanceSchedule != null) this.maintenanceSchedule = maintenanceSchedule;
        if (plantLocation != null) this.plantLocation = plantLocation;
        if (potType != null) this.potType = potType;
    }
}


