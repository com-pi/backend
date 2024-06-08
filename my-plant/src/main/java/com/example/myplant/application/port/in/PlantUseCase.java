package com.example.myplant.application.port.in;

import com.example.myplant.adapter.in.web.command.PlantCommand;
import com.example.myplant.domain.Plant;

import java.util.List;

public interface PlantUseCase {

    Long createPlant(PlantCommand command);
    Long updatePlant(Long plantId, PlantCommand command);
    void deletePlant(Long plantId);
    List<Plant> getPlantsByMemberId(Long memberId);
    Plant getPlantById(Long plantId);
}
