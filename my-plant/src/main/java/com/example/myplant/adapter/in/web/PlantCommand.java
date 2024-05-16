package com.example.myplant.adapter.in.web;

import com.example.common.baseentity.SelfValidating;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class PlantCommand extends SelfValidating<PlantCommand> {
    @NotNull
    private Long memberId;

    @NotBlank
    private String plantName;

    @NotBlank
    private String plantType;

    @NotNull
    private int plantAge;

    @NotNull
    private LocalDate plantBirthday;

    @NotNull
    private List<String> plantImageUrls;

    @NotNull
    private int wateringIntervalInWeeks;

    @NotNull
    private int wateringFrequency;

    @NotNull
    private LocalDate repottingDate;

    @NotNull
    private LocalDate fertilizingDate;

    @NotNull
    private LocalDate pruningDate;

    @NotBlank
    private String plantLocation;

    @NotBlank
    private String potType;

    // validation을 위한 public method 추가
    public void validate() {
        this.validateSelf();
    }
}
