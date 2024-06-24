package com.example.encycloservice.adapter.in.response;

import com.example.encycloservice.application.port.out.EncyclopediaQuery;
import com.example.encycloservice.application.port.out.PopularPlantStat;
import com.example.encycloservice.domain.PlantSpecies;
import lombok.Builder;

import java.util.List;

@Builder
public record PopularPlantStatResponse (
        String referenceTime,
        List<PlantRankRecord> plantRecord
) {
   @Builder
   public record PlantRankRecord (
           String id,
           int rank,
           String commonName,
           String imageUrl
   ) {
   }

   public static PopularPlantStatResponse of (PopularPlantStat domain, EncyclopediaQuery encyclopediaQuery){
      List<PlantRankRecord> plantRankRecord = domain.plantRankList().stream().map(
              plantRank -> {
                 PlantSpecies plantSpecies = encyclopediaQuery.getById(Long.valueOf(plantRank.plantId()));
                 return PlantRankRecord.builder()
                         .id(plantRank.plantId())
                         .rank(plantRank.rank())
                         .commonName(plantSpecies.getCommonName())
                         .imageUrl(plantSpecies.getImageUrls().get(0))
                         .build();
              }
      ).toList();
      return PopularPlantStatResponse.builder()
              .referenceTime(domain.referenceTime())
              .plantRecord(plantRankRecord)
              .build();
   }
}
