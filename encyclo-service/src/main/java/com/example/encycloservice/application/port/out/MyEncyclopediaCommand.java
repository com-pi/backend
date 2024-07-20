package com.example.encycloservice.application.port.out;

import com.example.encycloservice.domain.MyEncyclopedia;
import com.example.encycloservice.domain.PlantSpecies;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface MyEncyclopediaCommand {

    void createEncyclopedia(MyEncyclopedia myEncyclopedia);

    void addPlantsToEncyclopedia(PlantSpecies plantSpeciesId, MyEncyclopedia myEncyclopediaId);

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void addPlantsToEncyclopedia(Long plantSpeciesId, MyEncyclopedia myEncyclopedia);

    void removePlantFromEncyclopedia(PlantSpecies plantSpecies, MyEncyclopedia myEncyclopedia);

    void removePlantFromEncyclopedia(Long plantSpeciesId, MyEncyclopedia myEncyclopedia);

    void removeEncyclopedia(Long myEncyclopediaId);

}
