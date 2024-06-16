package com.example.encycloservice.adapter.out.cache;

import com.example.encycloservice.adapter.in.RecentSearchKeywordStat;
import com.example.encycloservice.application.port.out.StatisticsQuery;
import com.example.encycloservice.domain.RecentPlantDetailStat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StatisticsQueryImpl implements StatisticsQuery {

    private final StatRepository statRepository;

    @Override
    public RecentPlantDetailStat getRecentPlantDetailStat(Integer page, Integer size) {
        return statRepository.getRecentPlantDetails(page, size);
    }

    @Override
    public RecentSearchKeywordStat getRecentSearchKeywordStat(Integer page, Integer size) {
        return statRepository.getRecentSearchKeyword(page, size);
    }
}
