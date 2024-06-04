package com.example.myplant.application.port.in;

import com.example.myplant.adapter.in.web.CreateCharacterCommand;
import com.example.myplant.domain.Character;

public interface CreateCharacterUseCase {
    Character addCharacter(CreateCharacterCommand command);
}
