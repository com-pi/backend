package com.example.encycloservice.adapter.in.response;

import com.example.encycloservice.domain.PlantBrief;
import com.example.encycloservice.domain.PlantTaxonomy;
import lombok.Builder;

import java.util.List;

@Builder
public record PlantIdentifyResponse(
        List<PlantEstimate> identificationResults,
        List<PlantBrief> dbResults
) {
    @Builder
    public record PlantEstimate(
            double score,
            PlantTaxonomy plantTaxonomy,
            List<String> commonNames
    ){
    }
}

