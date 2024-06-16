package com.example.encycloservice.domain;

import com.example.encycloservice.adapter.out.cache.RecentPlantDetailRecord;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class RecentPlantDetailStat {

    private int totalElement;
    private int totalPage;
    private List<RecentPlantDetailRank> results;

    @Builder
    public record RecentPlantDetailRank(
         int rank,
         RecentPlantDetailRecord recentPlantDetailRecord
    ) {
    }

}
