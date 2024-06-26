package com.example.encycloservice.application.port.out;

import com.example.encycloservice.domain.MyEncyclopedia;
import com.example.encycloservice.domain.PlantSpecies;

public interface MyEncyclopediaCommand {

    void createEncyclopedia(MyEncyclopedia myEncyclopedia);
    void updateEncyclopedia(MyEncyclopedia myEncyclopedia);

    void addPlantToEncyclopedia(PlantSpecies plantSpeciesId, MyEncyclopedia myEncyclopediaId);
    void removePlantFromEncyclopedia(PlantSpecies plantSpecies, MyEncyclopedia myEncyclopedia);

}
