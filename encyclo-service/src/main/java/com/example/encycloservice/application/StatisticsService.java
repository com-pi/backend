package com.example.encycloservice.application;

import com.example.encycloservice.adapter.in.RecentSearchKeywordStat;
import com.example.encycloservice.application.port.in.StatisticsUseCase;
import com.example.encycloservice.application.port.out.StatisticsQuery;
import com.example.encycloservice.domain.RecentPlantDetailStat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticsService implements StatisticsUseCase {

    private final StatisticsQuery statisticsQuery;

    @Override
    public RecentSearchKeywordStat getRecentSearchKeywordStat(Integer page, Integer size) {
        return statisticsQuery.getRecentSearchKeywordStat(page, size);
    }

    @Override
    public RecentPlantDetailStat getRecentPlantDetailsList(Integer page, Integer size) {
        return statisticsQuery.getRecentPlantDetailStat(page, size);
    }

}
