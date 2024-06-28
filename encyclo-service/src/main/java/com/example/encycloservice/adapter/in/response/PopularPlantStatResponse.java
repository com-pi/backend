package com.example.encycloservice.adapter.in.response;

import com.example.encycloservice.application.port.out.EncyclopediaQuery;
import com.example.encycloservice.domain.PlantBrief;
import lombok.Builder;

import java.util.List;

@Builder
public record PopularPlantStatResponse(
        String referenceTime,
        List<PlantRankRecord> plantRecord
) {
   @Builder
   public record PlantRankRecord (
           int rank,
           PlantBrief plantBriefInfo
   ) {
   }

   public static PopularPlantStatResponse toResponse(
           com.example.encycloservice.application.port.out.PopularPlantStatResult result,
           EncyclopediaQuery encyclopediaQuery){

         return PopularPlantStatResponse.builder()
                .referenceTime(result.referenceTime())
                .plantRecord(result.plantRankList().stream()
                          .map(plantRank -> PlantRankRecord.builder()
                                 .rank(plantRank.rank())
                                 .plantBriefInfo(encyclopediaQuery.getBriefById(Long.valueOf(plantRank.plantId())))
                                 .build())
                          .toList())
                .build();
   }

}
