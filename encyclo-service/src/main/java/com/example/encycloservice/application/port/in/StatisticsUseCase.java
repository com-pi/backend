package com.example.encycloservice.application.port.in;

import com.example.encycloservice.adapter.in.RecentSearchKeywordStat;
import com.example.encycloservice.domain.RecentPlantDetailStat;

public interface StatisticsUseCase {

    RecentSearchKeywordStat getRecentSearchKeywordStat(Integer page, Integer size);
    RecentPlantDetailStat getRecentPlantDetailsList(Integer page, Integer size);
    
}
