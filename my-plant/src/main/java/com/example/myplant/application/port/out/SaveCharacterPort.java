package com.example.myplant.application.port.out;

import com.example.myplant.domain.PlantCharacter;

public interface SaveCharacterPort {
    PlantCharacter saveCharacter(PlantCharacter plantCharacter);
    void deleteCharacter(PlantCharacter plantCharacter);
}
