package com.example.myplant.adapter.out.persistence;

import com.example.myplant.application.port.out.*;
import com.example.myplant.domain.Character;
import com.example.myplant.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PlantPersistenceAdapter implements SavePlantPort, FindPlantPort, SaveCharacterPort, LoadCharacterPort, SaveCharacterIllustrationPort,
        LoadCharacterIllustrationPort, SavePlantCharacterPort, LoadPlantCharacterPort, LoadCalendarPort, SaveCalendarPort, LoadPlantDiaryPort, SavePlantDiaryPort, LoadDiaryPort, SaveDiaryPort {

    private final PlantRepository plantRepository;
    private final CharacterRepository characterRepository;
    private final CharacterIllustrationRepository characterIllustrationRepository;
    private final PlantCharacterRepository plantCharacterRepository;
    private final CalendarRepository calendarRepository;
    private final PlantDiaryRepository plantDiaryRepository;
    private final DiaryRepository diaryRepository;

    @Autowired
    public PlantPersistenceAdapter(
            PlantRepository plantRepository,
            CharacterRepository characterRepository,
            CharacterIllustrationRepository characterIllustrationRepository,
            PlantCharacterRepository plantCharacterRepository,
            CalendarRepository calendarRepository,
            PlantDiaryRepository plantDiaryRepository,
            DiaryRepository diaryRepository) {
        this.plantRepository = plantRepository;
        this.characterRepository = characterRepository;
        this.characterIllustrationRepository = characterIllustrationRepository;
        this.plantCharacterRepository = plantCharacterRepository;
        this.calendarRepository = calendarRepository;
        this.plantDiaryRepository = plantDiaryRepository;
        this.diaryRepository = diaryRepository;
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
    public Optional<Character> findCharacterById(Long characterId) {
        return plantRepository.findByCharacterId(characterId);
    }

    @Override
    public Character saveCharacter(Character character) {
        return characterRepository.save(character);
    }

    @Override
    public Optional<Character> loadCharacterById(Long id) {
        return characterRepository.findById(id);
    }

    @Override
    public List<Character> loadAllCharacters() {
        return characterRepository.findAll();
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
    public Calendar saveCalendar(Calendar calendar) {
        return calendarRepository.save(calendar);
    }

    @Override
    public Optional<Calendar> loadCalendarById(Long id) {
        return calendarRepository.findById(id);
    }

    @Override
    public List<Calendar> loadAllCalendars() {
        return calendarRepository.findAll();
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

    @Override
    public Diary saveDiary(Diary diary) {
        return diaryRepository.save(diary);
    }

    @Override
    public Optional<Diary> loadDiaryById(Long id) {
        return diaryRepository.findById(id);
    }

    @Override
    public List<Diary> loadAllDiaries() {
        return diaryRepository.findAll();
    }

}