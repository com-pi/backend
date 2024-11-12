package com.example.encycloservice.adapter.out.cache;

import com.example.encycloservice.adapter.out.persistence.RecentPlantDetailStatResult;
import com.example.encycloservice.application.port.out.PopularPlantStatResult;

import java.time.LocalDateTime;

public interface StatRepository {

    void recordRecentPlantDetails(Long plantId, long epochTimeStamp);
    void recordPopularPlant(Long plantId, Long memberId, LocalDateTime time);
    void updatePopularPlantStat(LocalDateTime now);
    RecentPlantDetailStatResult getRecentPlantDetails(int page, int size);
    PopularPlantStatResult getPopularPlantList();

}
