package com.example.myplant.application.port.out;

import com.example.myplant.domain.PlantCharacter;

public interface SavePlantCharacterPort {
    PlantCharacter savePlantCharacter(PlantCharacter plantCharacter);
}