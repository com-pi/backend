package com.example.encycloservice.application.port.out;

import com.example.encycloservice.domain.PlantSpecies;
import org.springframework.scheduling.annotation.Async;

public interface EncyclopediaStatCommand {

    @Async
    void recordRecentSearchKeyword(String keyword, long epochTimeStamp);

    @Async
    void recordRecentPlantDetails(PlantSpecies plantSpecies, long epochTimeStamp);

}
