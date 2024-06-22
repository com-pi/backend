package com.example.encycloservice.application.port.out;

import com.example.encycloservice.adapter.in.response.PopularPlantStatResponse;
import com.example.encycloservice.domain.RecentPlantDetailStat;

public interface StatisticsQuery {

    RecentPlantDetailStat getRecentPlantDetailStat(Integer page, Integer size);
    PopularPlantStatResponse getPopularPlantStat();
}
