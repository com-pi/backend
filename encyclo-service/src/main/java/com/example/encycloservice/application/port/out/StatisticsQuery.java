package com.example.encycloservice.application.port.out;

import com.example.encycloservice.adapter.out.persistence.RecentPlantDetailStatResult;

public interface StatisticsQuery {

    RecentPlantDetailStatResult getRecentPlantDetailStat(Integer page, Integer size);
    PopularPlantStatResult getPopularPlantStat();
}
