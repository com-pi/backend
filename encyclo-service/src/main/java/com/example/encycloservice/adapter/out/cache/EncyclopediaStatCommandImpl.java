package com.example.encycloservice.adapter.out.cache;

import com.example.encycloservice.application.port.out.EncyclopediaStatCommand;
import com.example.encycloservice.domain.PlantSpecies;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EncyclopediaStatCommandImpl implements EncyclopediaStatCommand {

    private final StatRepository statRepository;

    @Async
    @Override
    public void recordRecentSearchKeyword(String keyword, long epochTimeStamp) {
        statRepository.recordRecentSearchKeyword(keyword, epochTimeStamp);
    }

    @Async
    @Override
    public void recordRecentPlantDetails(PlantSpecies plantSpecies, long epochTimeStamp) {
        statRepository.recordRecentPlantDetails(RecentPlantDetailRecord.fromDomain(plantSpecies), epochTimeStamp);
    }
}
