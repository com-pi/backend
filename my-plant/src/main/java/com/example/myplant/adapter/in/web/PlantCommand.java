package com.example.myplant.adapter.in.web;

import com.example.common.baseentity.SelfValidating;
import lombok.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class PlantCommand extends SelfValidating<PlantCommand> {


    private Long memberId;

    @NotNull
    private String plantName;

    @NotNull
    private String plantType;

    private int plantAge;

    @NotNull
    private LocalDate plantBirthday;
    @NotNull
    private LocalDate lastWaterday;

    @NotNull
    private int wateringIntervalInDays;

    private LocalDate repottingDate;
    private LocalDate fertilizingDate;
    private LocalDate pruningDate;
    private int maintenanceIntervalInMonths;
    private String plantLocation;
    private String potType;

    @Builder
    public PlantCommand(String plantName, String plantType, int plantAge, LocalDate plantBirthday,
                        LocalDate lastWaterday, int wateringIntervalInDays, LocalDate repottingDate,
                        LocalDate fertilizingDate, LocalDate pruningDate, int maintenanceIntervalInMonths,
                        String plantLocation, String potType) {
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
    }
}
