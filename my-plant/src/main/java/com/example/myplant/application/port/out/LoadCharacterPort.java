package com.example.myplant.application.port.out;

import com.example.myplant.domain.PlantCharacter;

import java.util.List;
import java.util.Optional;

public interface LoadCharacterPort {
    Optional<PlantCharacter> loadCharacterById(Long id);
    List<PlantCharacter> loadAllCharacters();
}
