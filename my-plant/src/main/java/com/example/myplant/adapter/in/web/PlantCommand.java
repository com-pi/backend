package com.example.myplant.adapter.in.web;

import com.example.common.baseentity.SelfValidating;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
public class PlantCommand extends SelfValidating<PlantCommand> {
    @NotNull
    private Long memberId;

    @NotBlank
    private String plantName;

    @NotBlank
    private String plantType;

    private int plantAge;

    @NotNull
    private LocalDate plantBirthday;
    private LocalDate lastWaterday;

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
    private int intimacy = 1; // 친밀도 초기값 1

}
