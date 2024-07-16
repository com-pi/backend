package com.example.encycloservice.application;

import com.example.encycloservice.domain.PlantBrief;

import java.util.List;

public record PlantBriefListResponse(
        List<PlantBrief> plantBriefList
) {
    public static PlantBriefListResponse toResponse(List<PlantBrief> plantBriefList) {
        return new PlantBriefListResponse(plantBriefList);
    }
}
