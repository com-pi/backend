package com.example.myplant.application.port.out;

import com.example.myplant.domain.PlantDiary;

public interface SavePlantDiaryPort {
    PlantDiary savePlantDiary(PlantDiary plantDiary);
}
