package com.example.encycloservice.adapter.out;

import java.util.List;


public record SearchPlantResultList(List<SearchPlantResult> results) {

    public record SearchPlantResult(String name, String thumbnailUrl) {
    }

}
