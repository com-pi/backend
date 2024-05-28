package com.example.myplant.application.port.in;

import com.example.myplant.domain.PlantCharacter;

public interface CreatePlantCharacterUseCase {
    PlantCharacter createPlantCharacter(PlantCharacter plantCharacter);
}