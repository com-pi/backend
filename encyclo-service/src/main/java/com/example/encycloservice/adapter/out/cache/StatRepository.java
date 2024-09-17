package com.example.encycloservice.adapter.out.cache;

import com.example.encycloservice.application.port.out.PopularPlantStatResult;
import com.example.encycloservice.adapter.out.persistence.RecentPlantDetailStatResult;

import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;

public interface StatRepository {

    void recordRecentPlantDetails(Long plantId, long epochTimeStamp);
    void recordPopularPlant(Long plantId, String time, Long memberId);
    void updatePopularPlantStat(LocalDateTime now, long time, TemporalUnit unit);

    RecentPlantDetailStatResult getRecentPlantDetails(int page, int size);
    PopularPlantStatResult getPopularPlantList();

}
