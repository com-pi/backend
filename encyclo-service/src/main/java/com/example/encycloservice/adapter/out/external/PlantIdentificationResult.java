package com.example.encycloservice.adapter.out.external;

import com.example.encycloservice.adapter.in.response.PlantIdentifyResponse;
import com.example.encycloservice.domain.PlantTaxonomy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlantIdentificationResult {
    private Query query;
    private String language;
    private String preferedReferential;
    private String bestMatch;
    private List<Result> results;
    private String version;
    private int remainingIdentificationRequests;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Query {
        private String project;
        private List<String> images;
        private List<String> organs;
        private boolean includeRelatedImages;
        private boolean noReject;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Result {
        private double score;
        private Species species;
        private Gbif gbif;
        private Powo powo;
        private Iucn iucn;

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Species {
            private String scientificNameWithoutAuthor;
            private String scientificNameAuthorship;
            private Genus genus;
            private Family family;
            private List<String> commonNames;
            private String scientificName;

            @Data
            @Builder
            @NoArgsConstructor
            @AllArgsConstructor
            public static class Genus {
                private String scientificNameWithoutAuthor;
                private String scientificNameAuthorship;
                private String scientificName;
            }

            @Data
            @Builder
            @NoArgsConstructor
            @AllArgsConstructor
            public static class Family {
                private String scientificNameWithoutAuthor;
                private String scientificNameAuthorship;
                private String scientificName;
            }
        }

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Gbif {
            private String id;
        }

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Powo {
            private String id;
        }

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Iucn {
            private String id;
            private String category;
        }
    }

    public PlantIdentifyResponse toResponse() {
        List<PlantIdentifyResponse.PlantEstimate> list = results.stream().map(result -> {
            PlantTaxonomy taxonomy = PlantTaxonomy.builder()
                    .species(result.getSpecies().getScientificNameWithoutAuthor())
                    .genus(result.getSpecies().getGenus().getScientificNameWithoutAuthor())
                    .family(result.getSpecies().getFamily().getScientificNameWithoutAuthor())
                    .build();
            return PlantIdentifyResponse.PlantEstimate.builder()
                    .score(result.score)
                    .plantTaxonomy(taxonomy)
                    .commonNames(result.getSpecies().commonNames)
                    .build();
        }).toList();

        return new PlantIdentifyResponse(list);
    }
}