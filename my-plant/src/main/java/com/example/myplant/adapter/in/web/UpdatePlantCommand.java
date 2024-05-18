package com.example.myplant.adapter.in.web;

import com.example.common.baseentity.SelfValidating;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class UpdatePlantCommand extends SelfValidating<UpdatePlantCommand> {
    @NotNull
    private Long memberId;

    @NotNull
    private String plantName;

    @NotNull
    private String plantType;

    private int plantAge;

    @NotNull
    private LocalDate plantBirthday;
    private LocalDate lastWaterday;
    @Setter
    private List<MultipartFile> plantImages;

    @NotNull
    private int wateringIntervalInWeeks;

    @NotNull
    private int wateringFrequency;

    private LocalDate repottingDate;
    private LocalDate fertilizingDate;
    private LocalDate pruningDate;
    private String plantLocation;
    private String potType;

}