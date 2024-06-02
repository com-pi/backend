package com.example.myplant.adapter.in.web;

import com.example.common.baseentity.SelfValidating;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class UpdatePlantCommand extends SelfValidating<UpdatePlantCommand> {

    private Long plantId;
    private String plantName;

    private String plantType;

    private int plantAge;

    private LocalDate plantBirthday;

    private LocalDate lastWaterday;

    private int wateringIntervalInDays;

    private int wateringFrequency;

    private LocalDate repottingDate;
    private LocalDate fertilizingDate;
    private LocalDate pruningDate;
    private int maintenanceIntervalInMonths;
    private String plantLocation;
    private String potType;
    private Long characterId;


    @Builder
    public UpdatePlantCommand(String plantName, String plantType, int plantAge, LocalDate plantBirthday,
                              LocalDate lastWaterday, int wateringIntervalInDays, LocalDate repottingDate,
                              LocalDate fertilizingDate, LocalDate pruningDate, int maintenanceIntervalInMonths,
                              String plantLocation, String potType, Long characterId) {
        this.plantName = plantName;
        this.plantType = plantType;
        this.plantAge = plantAge;
        this.plantBirthday = plantBirthday;
        this.lastWaterday = lastWaterday;
        this.wateringIntervalInDays = wateringIntervalInDays;
        this.repottingDate = repottingDate;
        this.fertilizingDate = fertilizingDate;
        this.pruningDate = pruningDate;
        this.maintenanceIntervalInMonths = maintenanceIntervalInMonths;
        this.plantLocation = plantLocation;
        this.potType = potType;
        this.characterId = characterId;
    }
}