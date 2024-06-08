package com.example.myplant.adapter.out.persistence;

import com.example.myplant.adapter.out.repository.*;
import com.example.myplant.application.port.out.*;
import com.example.myplant.domain.Character;
import com.example.myplant.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PlantPersistenceAdapter implements PlantPort, SaveCharacterIllustrationPort,
        LoadCharacterIllustrationPort, SavePlantCharacterPort, LoadPlantCharacterPort,LoadPlantDiaryPort, SavePlantDiaryPort{

    private final PlantRepository plantRepository;
    private final CharacterRepository characterRepository;
    private final CharacterIllustrationRepository characterIllustrationRepository;
    private final PlantCharacterRepository plantCharacterRepository;
    private final PlantDiaryRepository plantDiaryRepository;

    @Autowired
    public PlantPersistenceAdapter(
            PlantRepository plantRepository,
            CharacterRepository characterRepository,
            CharacterIllustrationRepository characterIllustrationRepository,
            PlantCharacterRepository plantCharacterRepository,
            PlantDiaryRepository plantDiaryRepository) {
        this.plantRepository = plantRepository;
        this.characterRepository = characterRepository;
        this.characterIllustrationRepository = characterIllustrationRepository;
        this.plantCharacterRepository = plantCharacterRepository;
        this.plantDiaryRepository = plantDiaryRepository;
    }

    @Override
    public Plant savePlant(Plant plant) {
        return plantRepository.save(plant);
    }

    @Override
    public Optional<Plant> findPlantById(Long id) {
        return plantRepository.findById(id);
    }

    @Override
    public Optional<Plant> findPlantByPlantId(Long plantId) {
        return plantRepository.findByPlantId(plantId);
    }

    @Override
    public List<Plant> findPlantsByMemberId(Long memberId) {
        return plantRepository.findByMemberId(memberId);
    }

    @Override
    public void deletePlantById(Long id) {
        plantRepository.deleteById(id);
    }

    @Override
    public Optional<Character> findCharacterById(Long characterId) {
        return characterRepository.findById(characterId);
    }

    @Override
    public CharacterIllustration saveCharacterIllustration(CharacterIllustration illustration) {
        return characterIllustrationRepository.save(illustration);
    }

    @Override
    public Optional<CharacterIllustration> loadCharacterIllustrationById(Long id) {
        return characterIllustrationRepository.findById(id);
    }

    @Override
    public List<CharacterIllustration> loadAllCharacterIllustrations() {
        return characterIllustrationRepository.findAll();
    }

    @Override
    public PlantCharacter savePlantCharacter(PlantCharacter plantCharacter) {
        return plantCharacterRepository.save(plantCharacter);
    }

    @Override
    public Optional<PlantCharacter> loadPlantCharacterById(Long id) {
        return plantCharacterRepository.findById(id);
    }

    @Override
    public List<PlantCharacter> loadAllPlantCharacters() {
        return plantCharacterRepository.findAll();
    }

    @Override
    public PlantDiary savePlantDiary(PlantDiary plantDiary) {
        return plantDiaryRepository.save(plantDiary);
    }

    @Override
    public Optional<PlantDiary> loadPlantDiaryById(Long id) {
        return plantDiaryRepository.findById(id);
    }

    @Override
    public List<PlantDiary> loadAllPlantDiaries() {
        return plantDiaryRepository.findAll();
    }
}