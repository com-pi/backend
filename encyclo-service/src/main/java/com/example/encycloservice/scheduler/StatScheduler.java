package com.example.encycloservice.scheduler;

import com.example.encycloservice.application.port.in.StatisticsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class StatScheduler {

    private final StatisticsUseCase statisticsUseCase;

    @Scheduled(cron = "0 0 * * * *")
    public void updatePopularPlantStat() {
        statisticsUseCase.updatePopularPlantList(LocalDateTime.now());
    }

}
