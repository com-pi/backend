package com.example.myplant.application.port.in;

import com.example.myplant.adapter.in.web.command.CharacterCommand;
import com.example.myplant.domain.Character;

import java.util.List;

public interface CharacterUseCase {
    Long createCharacter(CharacterCommand command);
    void updateCharacter(Long id, CharacterCommand command);
    void deleteCharacter(Long id);
    Character getCharacterById(Long id);
    List<Character> getAllCharacters();
}