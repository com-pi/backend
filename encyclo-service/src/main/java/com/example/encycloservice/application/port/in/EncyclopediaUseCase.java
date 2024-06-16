package com.example.encycloservice.application.port.in;

import com.example.encycloservice.domain.PlantSpecies;
import com.example.encycloservice.domain.PlantSpeciesCreate;
import com.example.encycloservice.domain.SearchPlantQueryResult;

public interface EncyclopediaUseCase {

    Long savePlantSpecies(PlantSpeciesCreate plantSpeciesCreate);
    void syncDatabase(String keyword);
    SearchPlantQueryResult searchByName(String keyword, int page, int size);
    PlantSpecies getPlantDetailById(Long id);
}
