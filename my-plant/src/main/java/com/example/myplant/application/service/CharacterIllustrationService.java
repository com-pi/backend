package com.example.myplant.application.service;

import com.example.myplant.application.port.in.CreateCharacterIllustrationUseCase;
import com.example.myplant.application.port.in.UpdateCharacterIllustrationUseCase;
import com.example.myplant.application.port.out.LoadCharacterIllustrationPort;
import com.example.myplant.application.port.out.SaveCharacterIllustrationPort;
import com.example.myplant.domain.CharacterIllustration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterIllustrationService implements CreateCharacterIllustrationUseCase, UpdateCharacterIllustrationUseCase {
    private final SaveCharacterIllustrationPort saveCharacterIllustrationPort;
    private final LoadCharacterIllustrationPort loadCharacterIllustrationPort;

    @Autowired
    public CharacterIllustrationService(SaveCharacterIllustrationPort saveCharacterIllustrationPort, LoadCharacterIllustrationPort loadCharacterIllustrationPort) {
        this.saveCharacterIllustrationPort = saveCharacterIllustrationPort;
        this.loadCharacterIllustrationPort = loadCharacterIllustrationPort;
    }

    @Override
    public CharacterIllustration createCharacterIllustration(CharacterIllustration illustration) {
        return saveCharacterIllustrationPort.saveCharacterIllustration(illustration);
    }

    @Override
    public CharacterIllustration updateCharacterIllustration(Long id, CharacterIllustration illustration) {
        CharacterIllustration existingIllustration = loadCharacterIllustrationPort.loadCharacterIllustrationById(id)
                .orElseThrow(() -> new RuntimeException("Character illustration not found"));
        existingIllustration.setName(illustration.getName());
        existingIllustration.setImageUrl(illustration.getImageUrl());
        existingIllustration.setRequiredLevel(illustration.getRequiredLevel());
        return saveCharacterIllustrationPort.saveCharacterIllustration(existingIllustration);
    }

    public List<CharacterIllustration> getAllCharacterIllustrations() {
        return loadCharacterIllustrationPort.loadAllCharacterIllustrations();
    }
}