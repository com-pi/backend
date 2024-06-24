package com.example.encycloservice.adapter.out.cache;

import com.example.encycloservice.application.port.out.PopularPlantStat;
import com.example.encycloservice.domain.RecentPlantDetailStat;

import java.time.LocalDateTime;

public interface StatRepository {

    void recordRecentPlantDetails(Long plantId, long epochTimeStamp);
    void recordPopularPlant(Long plantId, String time, Long memberId);
    void updatePopularPlantStat(LocalDateTime now);

    RecentPlantDetailStat getRecentPlantDetails(int page, int size);
    PopularPlantStat getPopularPlantList();

}
