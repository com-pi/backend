package com.example.myplant.application.service;

import com.example.myplant.adapter.in.web.PlantCommand;
import com.example.myplant.application.port.in.RegistPlantUseCase;
import com.example.myplant.application.port.out.FindPlantPort;
import com.example.myplant.application.port.out.SavePlantPort;
import com.example.myplant.domain.Plant;
import com.example.myplant.domain.WateringInfo;
import com.example.myplant.domain.MaintenanceSchedule;
import com.example.myplant.domain.Character;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RegistPlantService implements RegistPlantUseCase {

    private final SavePlantPort savePlantPort;
    private final FindPlantPort findPlantPort;

    @Autowired
    public RegistPlantService(SavePlantPort savePlantPort, FindPlantPort findPlantPort) {
        this.savePlantPort = savePlantPort;
        this.findPlantPort = findPlantPort;
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

        Character character = findPlantPort.findCharacterById(command.getCharacterId())
                .orElseThrow(() -> new RuntimeException("Character not found"));

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
                .character(character)
                .build();
        return savePlantPort.savePlant(plant);
    }
}