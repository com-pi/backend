package com.example.encycloservice.application.port.out;

import com.example.encycloservice.domain.PlantSpecies;
import com.example.encycloservice.domain.SearchPlantQueryResult;

import java.util.Optional;

public interface EncyclopediaQuery {

    SearchPlantQueryResult searchByKeyword(String keyword, int page, int size);

    Optional<PlantSpecies> findById(Long id);

}
