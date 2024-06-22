package com.example.encycloservice.application.port.out;

import java.util.List;

public record PopularPlantStat(
        String referenceTime,
        List<PlantRank> plantRankList
) {
    public record PlantRank(
            int rank,
            String plantId
    ){ }
}
