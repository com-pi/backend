package com.example.myplant.application.port.in;

import com.example.myplant.domain.CharacterIllustration;

public interface UpdateCharacterIllustrationUseCase {
    CharacterIllustration updateCharacterIllustration(Long id, CharacterIllustration illustration);
}
