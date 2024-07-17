package com.example.encycloservice.application.port.out;

import com.example.encycloservice.domain.PlantAddInquiry;
import com.example.encycloservice.domain.PlantSpecies;

public interface EncyclopediaCommand {

    Long savePlantSpecies(PlantSpecies plantSpecies);
    void syncDatabaseFromExternal(String id);

    void savePlantAddInquiry(PlantAddInquiry plantAddInquiry);

    void processPlantAddInquiry(PlantAddInquiry processed);
}
