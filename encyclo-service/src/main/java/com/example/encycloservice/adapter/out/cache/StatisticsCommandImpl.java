package com.example.encycloservice.adapter.out.cache;

import com.example.encycloservice.application.port.out.StatisticsCommand;
import com.example.encycloservice.domain.PlantSpecies;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalUnit;

@Component
@RequiredArgsConstructor
public class StatisticsCommandImpl implements StatisticsCommand {

    private final StatRepository statRepository;
    private final DateTimeFormatter dateTimeHour = DateTimeFormatter.ofPattern("MMddHH");


    @Async
    @Override
    public void recordRecentPlantDetails(PlantSpecies plantSpecies, LocalDateTime now) {
        long currentTimeMillis = now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        statRepository.recordRecentPlantDetails(plantSpecies.getId(), currentTimeMillis);
    }

    @Async
    @Override
    public void recordPopularPlant(PlantSpecies plantSpecies, Long memberId, LocalDateTime now) {
        statRepository.recordPopularPlant(plantSpecies.getId(), now.format(dateTimeHour), memberId);
    }

    @Override
    public void updatePopularPlantStat(LocalDateTime now, long timeAmount, TemporalUnit unit) {
        statRepository.updatePopularPlantStat(now, timeAmount, unit);
    }

}
