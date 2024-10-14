package com.example.encycloservice.application.port.out;

import com.example.encycloservice.domain.PlantSpecies;
import org.springframework.scheduling.annotation.Async;

import java.time.LocalDateTime;

public interface StatisticsCommand {

    @Async
    void recordRecentPlantDetails(PlantSpecies plantSpecies, LocalDateTime timeStamp);

    @Async
    void recordPopularPlant(PlantSpecies plantSpecies, Long memberId, LocalDateTime timeStamp);

    void updatePopularPlantStat(LocalDateTime now);

}
