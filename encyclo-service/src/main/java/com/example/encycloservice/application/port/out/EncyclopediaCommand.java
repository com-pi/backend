package com.example.encycloservice.application.port.out;

import com.example.encycloservice.domain.PlantSpecies;

public interface EncyclopediaCommand {

    Long savePlantSpecies(PlantSpecies plantSpecies);
    void syncDatabaseFromExternal(String id);

}
