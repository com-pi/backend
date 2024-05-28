package com.example.myplant.application.port.in;

import com.example.myplant.domain.PlantDiary;

public interface CreatePlantDiaryUseCase {
    PlantDiary createPlantDiary(PlantDiary plantDiary);
}
