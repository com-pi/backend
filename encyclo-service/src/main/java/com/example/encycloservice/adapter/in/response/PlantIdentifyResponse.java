package com.example.encycloservice.adapter.in.response;

import com.example.encycloservice.domain.PlantTaxonomy;
import lombok.Builder;

import java.util.List;

@Builder
public record PlantIdentifyResponse(
        List<PlantEstimate> results
) {
    @Builder
    public record PlantEstimate(
            double score,
            PlantTaxonomy plantTaxonomy,
            List<String> commonNames
    ){
    }
}

