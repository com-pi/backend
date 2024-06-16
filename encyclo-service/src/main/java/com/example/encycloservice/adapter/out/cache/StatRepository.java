package com.example.encycloservice.adapter.out.cache;

import com.example.encycloservice.adapter.in.RecentSearchKeywordStat;
import com.example.encycloservice.domain.RecentPlantDetailStat;

public interface StatRepository {

    void recordRecentSearchKeyword(String keyword, long localDateTime);
    void recordRecentPlantDetails(RecentPlantDetailRecord plantRecord, long epochTimeStamp);

    RecentPlantDetailStat getRecentPlantDetails(int page, int size);
    RecentSearchKeywordStat getRecentSearchKeyword(int page, int size);
}
