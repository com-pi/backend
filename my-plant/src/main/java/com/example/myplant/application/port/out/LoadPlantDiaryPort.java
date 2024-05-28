package com.example.myplant.application.port.out;

import com.example.myplant.domain.PlantDiary;

import java.util.List;
import java.util.Optional;

public interface LoadPlantDiaryPort {
    Optional<PlantDiary> loadPlantDiaryById(Long id);
    List<PlantDiary> loadAllPlantDiaries();
}
