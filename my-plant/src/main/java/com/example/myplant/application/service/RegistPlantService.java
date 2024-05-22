package com.example.myplant.application.service;

import com.example.myplant.adapter.in.web.PlantCommand;
import com.example.myplant.application.port.in.RegistPlantUseCase;
import com.example.myplant.application.port.out.SavePlantPort;
import com.example.myplant.domain.Plant;
import com.example.myplant.domain.WateringInfo;
import com.example.myplant.domain.MaintenanceSchedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RegistPlantService implements RegistPlantUseCase {

    private static final Logger logger = LoggerFactory.getLogger(RegistPlantService.class);
    private final SavePlantPort savePlantPort;


    @Autowired
    public RegistPlantService(SavePlantPort savePlantPort) {
        this.savePlantPort = savePlantPort;
    }

    @Override
    public Plant registerPlant(PlantCommand command) {
        WateringInfo wateringInfo = WateringInfo.builder()
                .intervalInDays(command.getWateringIntervalInDays())
                .build();

        MaintenanceSchedule maintenanceSchedule = MaintenanceSchedule.builder()
                .repottingDate(command.getRepottingDate())
                .fertilizingDate(command.getFertilizingDate())
                .pruningDate(command.getPruningDate())
                .intervalInMonths(command.getMaintenanceIntervalInMonths())
                .build();

        Plant plant = Plant.builder()
                .memberId(command.getMemberId())
                .plantName(command.getPlantName())
                .plantType(command.getPlantType())
                .plantAge(command.getPlantAge())
                .plantBirthday(command.getPlantBirthday())
                .lastWaterday(command.getLastWaterday())
                .wateringInfo(wateringInfo)
                .maintenanceSchedule(maintenanceSchedule)
                .plantLocation(command.getPlantLocation())
                .potType(command.getPotType())
                .intimacy(1) // 친밀도 초기값 1
                .build();

        Plant savedPlant = savePlantPort.savePlant(plant);

        logger.info("Registered Plant - id: {}, memberId: {}, plantId: {}", savedPlant.getId(), savedPlant.getMemberId(), savedPlant.getPlantId());

        return savedPlant;
    }
}