package com.example.myplant.application.service;

import com.example.imagemodule.application.port.SaveImagesCommand;
import com.example.imagemodule.domain.MinioBucket;
import com.example.myplant.adapter.in.web.UpdatePlantCommand;
import com.example.myplant.application.port.in.UpdatePlantUseCase;
import com.example.myplant.application.port.out.SavePlantPort;
import com.example.myplant.application.port.out.FindPlantPort;
import com.example.imagemodule.application.port.ImageCommandPort;
import com.example.myplant.domain.Plant;
import com.example.myplant.domain.WateringInfo;
import com.example.myplant.domain.MaintenanceSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UpdatePlantService implements UpdatePlantUseCase{

    private final SavePlantPort savePlantPort;
    private final FindPlantPort findPlantPort;
    private final ImageCommandPort imageCommandPort;

    @Autowired
    public UpdatePlantService(SavePlantPort savePlantPort, FindPlantPort findPlantPort, ImageCommandPort imageCommandPort) {
        this.savePlantPort = savePlantPort;
        this.findPlantPort = findPlantPort;
        this.imageCommandPort = imageCommandPort;
    }

    @Override
    public Plant updatePlant(Long plantId, UpdatePlantCommand command) {
        Plant existingPlant = findPlantPort.findPlantById(plantId).orElseThrow(() -> new RuntimeException("Plant not found"));

        List<String> imageUrls = command.getPlantImages() != null ? imageCommandPort.saveImages(new SaveImagesCommand(command.getPlantImages(),MinioBucket.PLANT_IMAGES)) : existingPlant.getPlantImageUrls();

        WateringInfo wateringInfo = command.getWateringIntervalInWeeks() != 0 || command.getWateringFrequency() != 0 ?
                WateringInfo.builder()
                        .intervalInWeeks(command.getWateringIntervalInWeeks())
                        .frequency(command.getWateringFrequency())
                        .build() : existingPlant.getWateringInfo();

        MaintenanceSchedule maintenanceSchedule = command.getRepottingDate() != null || command.getFertilizingDate() != null || command.getPruningDate() != null ?
                MaintenanceSchedule.builder()
                        .repottingDate(command.getRepottingDate())
                        .fertilizingDate(command.getFertilizingDate())
                        .pruningDate(command.getPruningDate())
                        .build() : existingPlant.getMaintenanceSchedule();

        existingPlant.updatePlant(command.getPlantName(), command.getPlantType(), command.getPlantAge(),
                command.getPlantBirthday(), command.getLastWaterday(),imageUrls, wateringInfo, maintenanceSchedule,
                command.getPlantLocation(), command.getPotType());

        return savePlantPort.savePlant(existingPlant);
    }
}