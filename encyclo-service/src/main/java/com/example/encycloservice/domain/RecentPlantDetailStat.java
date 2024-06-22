package com.example.encycloservice.domain;

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
         Long plantId
    ) {
    }

}
