package com.example.myplant.application.service;

import com.example.myplant.application.port.in.CreatePlantDiaryUseCase;
import com.example.myplant.application.port.in.UpdatePlantDiaryUseCase;
import com.example.myplant.application.port.out.LoadPlantDiaryPort;
import com.example.myplant.application.port.out.SavePlantDiaryPort;
import com.example.myplant.domain.PlantDiary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlantDiaryService implements CreatePlantDiaryUseCase, UpdatePlantDiaryUseCase {
    private final SavePlantDiaryPort savePlantDiaryPort;
    private final LoadPlantDiaryPort loadPlantDiaryPort;

    @Autowired
    public PlantDiaryService(SavePlantDiaryPort savePlantDiaryPort, LoadPlantDiaryPort loadPlantDiaryPort) {
        this.savePlantDiaryPort = savePlantDiaryPort;
        this.loadPlantDiaryPort = loadPlantDiaryPort;
    }

    @Override
    public PlantDiary createPlantDiary(PlantDiary plantDiary) {
        return savePlantDiaryPort.savePlantDiary(plantDiary);
    }

    @Override
    public PlantDiary updatePlantDiary(Long id, PlantDiary plantDiary) {
        PlantDiary existingPlantDiary = loadPlantDiaryPort.loadPlantDiaryById(id)
                .orElseThrow(() -> new RuntimeException("Plant diary not found"));
        existingPlantDiary.setPlant(plantDiary.getPlant());
        existingPlantDiary.setCalendar(plantDiary.getCalendar());
        existingPlantDiary.setDiary(plantDiary.getDiary());
        existingPlantDiary.setIntimacyScore(plantDiary.getIntimacyScore());
        return savePlantDiaryPort.savePlantDiary(existingPlantDiary);
    }

    public List<PlantDiary> getAllPlantDiaries() {
        return loadPlantDiaryPort.loadAllPlantDiaries();
    }
}