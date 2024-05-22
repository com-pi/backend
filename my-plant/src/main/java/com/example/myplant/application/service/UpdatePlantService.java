package com.example.myplant.application.service;

import com.example.myplant.adapter.in.web.UpdatePlantCommand;
import com.example.myplant.application.port.in.UpdatePlantUseCase;
import com.example.myplant.application.port.out.SavePlantPort;
import com.example.myplant.application.port.out.FindPlantPort;
import com.example.myplant.domain.Plant;
import com.example.myplant.domain.WateringInfo;
import com.example.myplant.domain.MaintenanceSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UpdatePlantService implements UpdatePlantUseCase{

    private static final Logger logger = LoggerFactory.getLogger(RegistPlantService.class);
    private final SavePlantPort savePlantPort;
    private final FindPlantPort findPlantPort;

    @Autowired
    public UpdatePlantService(SavePlantPort savePlantPort, FindPlantPort findPlantPort) {
        this.savePlantPort = savePlantPort;
        this.findPlantPort = findPlantPort;
    }

    @Override
    public Plant updatePlant(Long plantId, UpdatePlantCommand command) {
        Plant existingPlant = findPlantPort.findPlantById(plantId).orElseThrow(() -> new RuntimeException("Plant not found"));

        WateringInfo wateringInfo = (command.getWateringIntervalInDays() != 0) ?
                WateringInfo.builder()
                        .intervalInDays(command.getWateringIntervalInDays())
                        .build() : existingPlant.getWateringInfo();

        MaintenanceSchedule maintenanceSchedule = (command.getMaintenanceIntervalInMonths() != 0 ||
                command.getRepottingDate() != null || command.getFertilizingDate() != null || command.getPruningDate() != null) ?
                MaintenanceSchedule.builder()
                        .repottingDate(command.getRepottingDate())
                        .fertilizingDate(command.getFertilizingDate())
                        .pruningDate(command.getPruningDate())
                        .intervalInMonths(command.getMaintenanceIntervalInMonths())
                        .build() : existingPlant.getMaintenanceSchedule();

        existingPlant.updatePlant(command.getPlantName(), command.getPlantType(), command.getPlantAge(),
                command.getPlantBirthday(), command.getLastWaterday(),wateringInfo, maintenanceSchedule,
                command.getPlantLocation(), command.getPotType());

        Plant existedPlant = savePlantPort.savePlant(existingPlant);

        logger.info("Registered Plant - id: {}, memberId: {}, plantId: {}", existedPlant.getId(), existedPlant.getMemberId(), existedPlant.getPlantId());

        return existedPlant;

        //return savePlantPort.savePlant(existingPlant);
    }
}