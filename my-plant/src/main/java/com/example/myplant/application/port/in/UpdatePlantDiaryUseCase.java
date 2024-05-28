package com.example.myplant.application.port.in;

import com.example.myplant.domain.PlantDiary;

public interface UpdatePlantDiaryUseCase {
    PlantDiary updatePlantDiary(Long id, PlantDiary plantDiary);
}
