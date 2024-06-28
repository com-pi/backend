package com.example.encycloservice.adapter.out.cache;

import com.example.encycloservice.application.port.out.PopularPlantStatResult;
import com.example.encycloservice.adapter.out.persistence.RecentPlantDetailStatResult;

import java.time.LocalDateTime;

public interface StatRepository {

    void recordRecentPlantDetails(Long plantId, long epochTimeStamp);
    void recordPopularPlant(Long plantId, String time, Long memberId);
    void updatePopularPlantStat(LocalDateTime now);

    RecentPlantDetailStatResult getRecentPlantDetails(int page, int size);
    PopularPlantStatResult getPopularPlantList();

}
