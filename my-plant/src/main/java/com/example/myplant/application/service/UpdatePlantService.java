package com.example.myplant.application.service;

import com.example.myplant.adapter.in.web.UpdatePlantCommand;
import com.example.myplant.application.port.in.UpdatePlantUseCase;
import com.example.myplant.application.port.out.LoadPlantPort;
import com.example.myplant.application.port.out.SavePlantPort;
import com.example.myplant.domain.MaintenanceSchedule;
import com.example.myplant.domain.Plant;
import com.example.myplant.domain.WateringInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdatePlantService implements UpdatePlantUseCase{

    private final LoadPlantPort loadPlantPort;
    private final SavePlantPort savePlantPort;

    @Autowired
    public UpdatePlantService(LoadPlantPort loadPlantPort, SavePlantPort savePlantPort) {
        this.loadPlantPort = loadPlantPort;
        this.savePlantPort = savePlantPort;
    }

    @Override
    public Plant updatePlant(UpdatePlantCommand command) {
        Optional<Plant> optionalPlant = loadPlantPort.loadPlantById(command.getId());
        if (optionalPlant.isPresent()) {
            Plant plant = optionalPlant.get();
            plant.setPlantName(command.getPlantName());
            plant.setPlantType(command.getPlantType());
            plant.setPlantAge(command.getPlantAge());
            plant.setPlantBirthday(command.getPlantBirthday());
            plant.setPlantImageUrls(command.getPlantImageUrls());
            plant.setWateringInfo(new WateringInfo(command.getWateringIntervalInWeeks(),command.getWateringFrequency()));
            plant.setMaintenanceSchedule(new MaintenanceSchedule(command.getRepottingDate(), command.getFertilizingDate(), command.getPruningDate()));
            plant.setPlantLocation(command.getPlantLocation());
            plant.setPotType(command.getPotType());

            return savePlantPort.savePlant(plant);
        } else {
            throw new IllegalArgumentException("Plant not found");
        }
    }
}
