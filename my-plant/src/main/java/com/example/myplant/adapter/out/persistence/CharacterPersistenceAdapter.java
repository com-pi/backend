package com.example.myplant.adapter.out.persistence;

import com.example.myplant.adapter.out.repository.CharacterRepository;
import com.example.myplant.application.port.out.LoadCharacterPort;
import com.example.myplant.application.port.out.SaveCharacterPort;
import com.example.myplant.domain.Character;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CharacterPersistenceAdapter implements SaveCharacterPort, LoadCharacterPort {

    private final CharacterRepository characterRepository;

    @Autowired
    public CharacterPersistenceAdapter(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    @Override
    public Character saveCharacter(Character character) {
        return characterRepository.save(character);
    }

    @Override
    public void deleteCharacter(Character character) {
        characterRepository.delete(character);
    }

    @Override
    public Optional<Character> loadCharacterById(Long id) {
        return characterRepository.findById(id);
    }

    @Override
    public List<Character> loadAllCharacters() {
        return characterRepository.findAll();
    }
}
