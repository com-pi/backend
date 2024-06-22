package com.example.encycloservice.adapter.in.response;

import lombok.Builder;

import java.util.List;

@Builder
public record PopularPlantStatResponse (
        List<PlantRankRecord> plantRecord
) {
   @Builder
   public record PlantRankRecord (
           Long id,
           String commonName,
           String imageUrl
   ) {
   }
}
