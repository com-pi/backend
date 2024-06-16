package com.example.encycloservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class SearchPlantQueryResult {

    private long totalElement;
    private int totalPage;
    private List<SingleSearchPlantResult> results;

    @Getter
    @AllArgsConstructor
    @Builder
    public static class SingleSearchPlantResult {
        private Long plantSpeciesId;
        private String commonName;
        private String imageUrl;
    }

}
