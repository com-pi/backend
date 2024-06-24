package com.example.encycloservice.application.port.out;

import com.example.encycloservice.adapter.out.cache.PopularityRecordRepository;

import java.util.List;

public record PopularPlantStat(
        String referenceTime,
        List<PopularityRecordRepository.PlantRank> plantRankList
) {
}
