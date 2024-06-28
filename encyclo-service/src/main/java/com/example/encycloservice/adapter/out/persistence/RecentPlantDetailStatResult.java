package com.example.encycloservice.adapter.out.persistence;

import lombok.Builder;

import java.util.List;

@Builder
public record RecentPlantDetailStatResult(
        int totalElement,
        int totalPage,
        List<RecentPlantDetailRank> results
) {

    @Builder
    public record RecentPlantDetailRank(
            int rank,
            Long plantId
    ) {
    }
}
