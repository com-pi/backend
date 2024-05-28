package com.example.myplant.application.port.in;

import com.example.myplant.domain.PlantCharacter;

public interface UpdatePlantCharacterUseCase {
    PlantCharacter updatePlantCharacter(Long id, PlantCharacter plantCharacter);
}