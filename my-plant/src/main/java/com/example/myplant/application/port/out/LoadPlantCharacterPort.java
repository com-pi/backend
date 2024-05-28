package com.example.myplant.application.port.out;

import com.example.myplant.domain.PlantCharacter;

import java.util.Optional;
import java.util.List;

public interface LoadPlantCharacterPort {
    Optional<PlantCharacter> loadPlantCharacterById(Long id);
    List<PlantCharacter> loadAllPlantCharacters();
}