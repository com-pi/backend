package com.example.myplant.adapter.in.web.command;

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

}