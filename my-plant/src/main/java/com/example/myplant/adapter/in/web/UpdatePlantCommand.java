package com.example.myplant.adapter.in.web;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UpdatePlantCommand {
    private Long id;
    private String plantName;
    private String plantType;
    private int plantAge;
    private LocalDate plantBirthday;
    private List<String> plantImageUrls;
    private int wateringIntervalInWeeks;
    private int wateringFrequency;
    private LocalDate repottingDate;
    private LocalDate fertilizingDate;
    private LocalDate pruningDate;
    private String plantLocation;
    private String potType;
}