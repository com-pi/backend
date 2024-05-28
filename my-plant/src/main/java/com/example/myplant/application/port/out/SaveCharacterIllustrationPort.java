package com.example.myplant.application.port.out;

import com.example.myplant.domain.CharacterIllustration;

public interface SaveCharacterIllustrationPort {
    CharacterIllustration saveCharacterIllustration(CharacterIllustration illustration);
}