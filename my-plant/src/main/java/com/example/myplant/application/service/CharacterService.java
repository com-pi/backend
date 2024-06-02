package com.example.myplant.application.service;

import com.example.myplant.application.port.in.CreateCharacterUseCase;
import com.example.myplant.application.port.in.UpdateCharacterUseCase;
import com.example.myplant.application.port.out.LoadCharacterPort;
import com.example.myplant.application.port.out.SaveCharacterPort;
import com.example.myplant.domain.Character;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterService implements CreateCharacterUseCase, UpdateCharacterUseCase {
    private final SaveCharacterPort saveCharacterPort;
    private final LoadCharacterPort loadCharacterPort;

    @Autowired
    public CharacterService(SaveCharacterPort saveCharacterPort, LoadCharacterPort loadCharacterPort) {
        this.saveCharacterPort = saveCharacterPort;
        this.loadCharacterPort = loadCharacterPort;
    }

    @Override
    public Character createCharacter(Character character) {
        return saveCharacterPort.saveCharacter(character);
    }

    @Override
    public Character updateCharacter(Long id, Character character) {
        Character existingCharacter = loadCharacterPort.loadCharacterById(id)
                .orElseThrow(() -> new RuntimeException("Character not found"));
        existingCharacter.setCharacterNo(character.getCharacterNo());
        existingCharacter.setCharacterName(character.getCharacterName());
        return saveCharacterPort.saveCharacter(existingCharacter);
    }

    public List<Character> getAllCharacters() {
        return loadCharacterPort.loadAllCharacters();
    }
}