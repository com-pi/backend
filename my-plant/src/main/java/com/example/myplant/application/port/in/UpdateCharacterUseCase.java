package com.example.myplant.application.port.in;

import com.example.myplant.adapter.in.web.UpdateCharacterCommand;
import com.example.myplant.domain.Character;
public interface UpdateCharacterUseCase {
    Character updateCharacter(UpdateCharacterCommand command);
}
