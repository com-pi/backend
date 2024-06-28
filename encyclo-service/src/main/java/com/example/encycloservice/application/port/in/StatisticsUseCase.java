package com.example.encycloservice.application.port.in;

import com.example.encycloservice.adapter.in.response.PopularPlantStatResponse;
import com.example.encycloservice.adapter.in.response.RecentPlantDetailResponse;

import java.time.LocalDateTime;

public interface StatisticsUseCase {

    RecentPlantDetailResponse getRecentPlantDetailsList(Integer page, Integer size);
    PopularPlantStatResponse getPopularPlantList();
    void updatePopularPlantList(LocalDateTime now);

}

