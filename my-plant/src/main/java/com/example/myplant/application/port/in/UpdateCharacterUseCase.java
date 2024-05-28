package com.example.myplant.application.port.in;

import com.example.myplant.domain.Character;
public interface UpdateCharacterUseCase {
    Character updateCharacter(Long id, Character character);
}
