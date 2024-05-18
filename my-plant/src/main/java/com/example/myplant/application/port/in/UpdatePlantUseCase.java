package com.example.myplant.application.port.in;

import com.example.myplant.adapter.in.web.UpdatePlantCommand;
import com.example.myplant.domain.Plant;

public interface UpdatePlantUseCase {
    Plant updatePlant(Long plantId, UpdatePlantCommand command);
}