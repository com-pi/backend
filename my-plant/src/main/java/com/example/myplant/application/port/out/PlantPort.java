package com.example.myplant.application.port.out;

import com.example.myplant.domain.PlantCharacter;
import com.example.myplant.domain.Plant;

import java.util.List;
import java.util.Optional;

public interface PlantPort {
    Plant savePlant(Plant plant);
    Optional<Plant> findPlantById(Long id);
    List<Plant> findPlantsByMemberId(Long memberId);
    void deletePlantById(Long id);
    Optional<Plant> findPlantByPlantId(Long plantId);
    Optional<PlantCharacter> findCharacterById(Long characterId);
}
