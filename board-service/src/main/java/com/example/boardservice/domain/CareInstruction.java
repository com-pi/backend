package com.example.boardservice.domain;

import lombok.*;

import java.time.LocalDate;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderMethodName = "generate")
public class CareInstruction {

    private LocalDate startDate;
    private LocalDate endDate;
    private String environment;
    private Integer wateringPeriod;
    private Integer wateringFrequency;
    private Integer minTemperature;
    private Integer maxTemperature;

}
