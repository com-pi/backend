package com.example.myplant.application.port.out;

import com.example.myplant.domain.CharacterIllustration;

import java.util.List;
import java.util.Optional;

public interface LoadCharacterIllustrationPort {
    Optional<CharacterIllustration> loadCharacterIllustrationById(Long id);
    List<CharacterIllustration> loadAllCharacterIllustrations();
}