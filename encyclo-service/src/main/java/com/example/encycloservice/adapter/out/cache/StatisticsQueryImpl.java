package com.example.encycloservice.adapter.out.cache;

import com.example.encycloservice.adapter.out.persistence.RecentPlantDetailStatResult;
import com.example.encycloservice.application.port.out.PopularPlantStatResult;
import com.example.encycloservice.application.port.out.StatisticsQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StatisticsQueryImpl implements StatisticsQuery {

    private final StatRepository statRepository;

    @Override
    public RecentPlantDetailStatResult getRecentPlantDetailStat(Integer page, Integer size) {
        return statRepository.getRecentPlantDetails(page, size);
    }

    @Override
    public PopularPlantStatResult getPopularPlantStat() {
        return statRepository.getPopularPlantList();
    }


}
