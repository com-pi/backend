package com.example.myplant.application.port.out;

import com.example.myplant.domain.Character;
import java.util.List;
import java.util.Optional;

public interface LoadCharacterPort {
    Optional<Character> loadCharacterById(Long id);
    List<Character> loadAllCharacters();
}
