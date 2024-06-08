package com.example.myplant.application.service;

import com.example.myplant.adapter.in.web.command.CharacterCommand;
import com.example.myplant.application.port.in.CharacterUseCase;
import com.example.myplant.application.port.out.LoadCharacterPort;
import com.example.myplant.application.port.out.SaveCharacterPort;
import com.example.myplant.domain.Character;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CharacterService implements CharacterUseCase {
    private final SaveCharacterPort saveCharacterPort;
    private final LoadCharacterPort loadCharacterPort;

    @Autowired
    public CharacterService(SaveCharacterPort saveCharacterPort, LoadCharacterPort loadCharacterPort) {
        this.saveCharacterPort = saveCharacterPort;
        this.loadCharacterPort = loadCharacterPort;
    }

    @Override
    public Long createCharacter(CharacterCommand command) {
        Character character = Character.builder()
                .characterNo(command.getCharacterNo())
                .characterName(command.getCharacterName())
                .build();
        return saveCharacterPort.saveCharacter(character).getId();
    }

    @Override
    public void updateCharacter(Long id, CharacterCommand command) {
        Optional<Character> optionalCharacter = loadCharacterPort.loadCharacterById(id);
        if (optionalCharacter.isPresent()) {
            Character character = optionalCharacter.get();
            character.setCharacterNo(command.getCharacterNo());
            character.setCharacterName(command.getCharacterName());
            saveCharacterPort.saveCharacter(character);
        } else {
            throw new RuntimeException("Character not found");
        }
    }

    @Override
    public void deleteCharacter(Long id) {
        loadCharacterPort.loadCharacterById(id)
                .ifPresent(character -> saveCharacterPort.deleteCharacter(character));
    }

    @Override
    public Character getCharacterById(Long id) {
        return loadCharacterPort.loadCharacterById(id)
                .orElseThrow(() -> new RuntimeException("Character not found"));
    }

    @Override
    public List<Character> getAllCharacters() {
        return loadCharacterPort.loadAllCharacters();
    }
}