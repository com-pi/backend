package com.example.encycloservice.application;

import com.example.encycloservice.adapter.in.response.PopularPlantStatResponse;
import com.example.encycloservice.adapter.in.response.RecentPlantDetailResponse;
import com.example.encycloservice.adapter.out.persistence.RecentPlantDetailStatResult;
import com.example.encycloservice.application.port.in.StatisticsUseCase;
import com.example.encycloservice.application.port.out.EncyclopediaQuery;
import com.example.encycloservice.application.port.out.StatisticsCommand;
import com.example.encycloservice.application.port.out.StatisticsQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class StatisticsService implements StatisticsUseCase {

    private final EncyclopediaQuery encyclopediaQuery;
    private final StatisticsQuery statisticsQuery;
    private final StatisticsCommand statisticsCommand;

    @Override
    public RecentPlantDetailResponse getRecentPlantDetailsList(Integer page, Integer size) {
        RecentPlantDetailStatResult recentPlantDetailStatResult = statisticsQuery.getRecentPlantDetailStat(page, size);
        return RecentPlantDetailResponse.toResponse(recentPlantDetailStatResult, encyclopediaQuery);
    }

    @Override
    public PopularPlantStatResponse getPopularPlantList() {
        return PopularPlantStatResponse.toResponse(statisticsQuery.getPopularPlantStat(), encyclopediaQuery);
    }

    @Override
    public void updatePopularPlantList(LocalDateTime now) {
        statisticsCommand.updatePopularPlantStat(now);
    }

}
