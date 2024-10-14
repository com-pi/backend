package com.example.encycloservice.adapter.out.cache;

import com.example.encycloservice.application.port.out.StatisticsCommand;
import com.example.encycloservice.domain.PlantSpecies;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
@RequiredArgsConstructor
public class StatisticsCommandImpl implements StatisticsCommand {

    private final StatRepository statRepository;


    @Async
    @Override
    public void recordRecentPlantDetails(PlantSpecies plantSpecies, LocalDateTime now) {
        long currentTimeSecond = now.atZone(ZoneId.systemDefault()).toEpochSecond();
        statRepository.recordRecentPlantDetails(plantSpecies.getId(), currentTimeSecond);
    }

    @Async
    @Override
    public void recordPopularPlant(PlantSpecies plantSpecies, Long memberId, LocalDateTime now) {
        statRepository.recordPopularPlant(plantSpecies.getId(), now, memberId);
    }

    @Override
    public void updatePopularPlantStat(LocalDateTime now) {
        statRepository.updatePopularPlantStat(now);
    }

}
