package com.example.encycloservice.application.port.out;

import com.example.encycloservice.domain.PlantBrief;
import com.example.encycloservice.domain.PlantSpecies;
import com.example.encycloservice.domain.SearchPlantQueryResult;

public interface EncyclopediaQuery {

    SearchPlantQueryResult searchByKeyword(String keyword, int page, int size);
    PlantSpecies getById(Long id);
    PlantBrief getBriefById(Long id);
}
