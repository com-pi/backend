package com.example.encycloservice.domain;

import java.util.List;


public record SearchPlantResultList(List<SearchPlantResult> results) {

    public record SearchPlantResult(String name, String thumbnailUrl) {
    }

}
