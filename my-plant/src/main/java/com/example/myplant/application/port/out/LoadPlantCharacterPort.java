package com.example.myplant.application.port.out;

import com.example.myplant.domain.PlantCharacter;

import java.util.List;
import java.util.Optional;

public interface LoadPlantCharacterPort {
    Optional<PlantCharacter> loadPlantCharacterById(Long id);
    List<PlantCharacter> loadAllPlantCharacters();
}