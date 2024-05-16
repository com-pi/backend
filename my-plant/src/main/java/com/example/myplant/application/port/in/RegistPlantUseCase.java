package com.example.myplant.application.port.in;

import com.example.myplant.adapter.in.web.PlantCommand;
import com.example.myplant.domain.Plant;

public interface RegistPlantUseCase {

    Plant registPlant(PlantCommand command);
}
