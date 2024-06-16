package com.example.encycloservice.application.port.out;

import com.example.encycloservice.adapter.in.RecentSearchKeywordStat;
import com.example.encycloservice.domain.RecentPlantDetailStat;

public interface StatisticsQuery {

    RecentPlantDetailStat getRecentPlantDetailStat(Integer page, Integer size);
    RecentSearchKeywordStat getRecentSearchKeywordStat(Integer page, Integer size);

}
