package com.example.encycloservice.adapter.out.cache;

import com.example.encycloservice.adapter.in.response.PopularPlantStatResponse;
import com.example.encycloservice.application.port.out.EncyclopediaQuery;
import com.example.encycloservice.application.port.out.PopularPlantStat;
import com.example.encycloservice.application.port.out.StatisticsQuery;
import com.example.encycloservice.domain.RecentPlantDetailStat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StatisticsQueryImpl implements StatisticsQuery {

    private final StatRepository statRepository;
    private final EncyclopediaQuery encyclopediaQuery;

    @Override
    public RecentPlantDetailStat getRecentPlantDetailStat(Integer page, Integer size) {
        return statRepository.getRecentPlantDetails(page, size);
    }

    @Override
    public PopularPlantStatResponse getPopularPlantStat() {
        PopularPlantStat popularPlantList = statRepository.getPopularPlantList();
        return PopularPlantStatResponse.of(popularPlantList, encyclopediaQuery);
    }


}
