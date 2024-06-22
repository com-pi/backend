package com.example.encycloservice.application.port.in;

import com.example.encycloservice.adapter.in.response.PopularPlantStatResponse;
import com.example.encycloservice.domain.RecentPlantDetailStat;

import java.time.LocalDateTime;

public interface StatisticsUseCase {

    RecentPlantDetailStat getRecentPlantDetailsList(Integer page, Integer size);
    PopularPlantStatResponse getPopularPlantList();
    void updatePopularPlantList(LocalDateTime now);

}

