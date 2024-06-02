package com.example.encycloservice.adapter.out;

import com.example.encycloservice.adapter.in.response.PlantIdentifyResponse;
import com.example.encycloservice.domain.PlantTaxonomy;

import java.util.List;

public record PlantIdentificationResult(
        List<Result> results
) {
    public record Result(
            double score,
            Species species
    ) {
        public record Species(
                String scientificNameWithoutAuthor,
                Genus genus,
                Family family,
                List<String> commonNames
        ) {
            public record Genus(
                    String scientificNameWithoutAuthor
            ) {
            }
            public record Family(
                    String scientificNameWithoutAuthor
            ) {
            }
        }
    }

    public PlantIdentifyResponse toResponse() {
        if(results.isEmpty()) {
            return null;
        }

        Result result = results.get(0);
        PlantTaxonomy plantTaxonomy = PlantTaxonomy.builder()
                .species(result.species.scientificNameWithoutAuthor)
                .genus(result.species.genus.scientificNameWithoutAuthor)
                .family(result.species.family.scientificNameWithoutAuthor)
                .build();

        return new PlantIdentifyResponse(
                result.score,
                plantTaxonomy,
                result.species().commonNames
        );
    }
}
