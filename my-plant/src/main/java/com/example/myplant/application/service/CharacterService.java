package com.example.myplant.application.service;

import com.example.myplant.adapter.in.web.CreateCharacterCommand;
import com.example.myplant.adapter.in.web.UpdateCharacterCommand;
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
    public Character addCharacter(CreateCharacterCommand command) {
        Character character = Character.builder()
                .characterNo(command.getCharacterNo())
                .characterName(command.getCharacterName())
                .build();
        return saveCharacterPort.saveCharacter(character);
    }

    @Override
    public Character updateCharacter(UpdateCharacterCommand command) {
        Character existingCharacter = loadCharacterPort.loadCharacterById(command.getId())
                .orElseThrow(() -> new RuntimeException("Character not found"));
        existingCharacter.setCharacterNo(command.getCharacterNo());
        existingCharacter.setCharacterName(command.getCharacterName());
        return saveCharacterPort.saveCharacter(existingCharacter);
    }

    public List<Character> getAllCharacters() {
        return loadCharacterPort.loadAllCharacters();
    }
}