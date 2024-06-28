package com.example.encycloservice.adapter.in.response;

import com.example.encycloservice.adapter.out.persistence.RecentPlantDetailStatResult;
import com.example.encycloservice.application.port.out.EncyclopediaQuery;
import com.example.encycloservice.domain.PlantBrief;
import lombok.Builder;

import java.util.List;

@Builder
public record RecentPlantDetailResponse(
        int totalElement,
        int totalPage,
        List<RecentPlantDetailRank> results
) {

    @Builder
    public record RecentPlantDetailRank(
         int rank,
         PlantBrief plantBriefInfo
    ) {
        public static RecentPlantDetailRank toResponse(
                RecentPlantDetailStatResult.RecentPlantDetailRank recentPlantDetailRank,
                EncyclopediaQuery encyclopediaQuery) {

            return RecentPlantDetailRank.builder()
                    .rank(recentPlantDetailRank.rank())
                    .plantBriefInfo(encyclopediaQuery.getBriefById(recentPlantDetailRank.plantId()))
                    .build();
        }
    }

    public static RecentPlantDetailResponse toResponse(
            RecentPlantDetailStatResult recentPlantDetailStatResult,
            EncyclopediaQuery encyclopediaQuery) {

        return RecentPlantDetailResponse.builder()
                .totalElement(recentPlantDetailStatResult.totalElement())
                .totalPage(recentPlantDetailStatResult.totalPage())
                .results(recentPlantDetailStatResult.results().stream()
                        .map(recentPlantDetailRank -> RecentPlantDetailRank.toResponse(recentPlantDetailRank, encyclopediaQuery))
                        .toList())
                .build();
    }

}
