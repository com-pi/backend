package com.example.encycloservice.application;

import com.example.encycloservice.adapter.in.response.PopularPlantStatResponse;
import com.example.encycloservice.application.port.in.StatisticsUseCase;
import com.example.encycloservice.application.port.out.StatisticsCommand;
import com.example.encycloservice.application.port.out.StatisticsQuery;
import com.example.encycloservice.domain.RecentPlantDetailStat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class StatisticsService implements StatisticsUseCase {

    private final StatisticsQuery statisticsQuery;
    private final StatisticsCommand statisticsCommand;

    @Override
    public RecentPlantDetailStat getRecentPlantDetailsList(Integer page, Integer size) {
        return statisticsQuery.getRecentPlantDetailStat(page, size);
    }

    @Override
    public PopularPlantStatResponse getPopularPlantList() {
        return statisticsQuery.getPopularPlantStat();
    }

    @Override
    public void updatePopularPlantList(LocalDateTime now) {
        statisticsCommand.updatePopularPlantStat(now);
    }

}
