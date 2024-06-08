package com.example.myplant.application.service;

import com.example.myplant.adapter.in.web.command.PlantCommand;
import com.example.myplant.application.port.in.PlantUseCase;
import com.example.myplant.application.port.out.PlantPort;
import com.example.myplant.domain.Character;
import com.example.myplant.domain.Plant;
import com.example.myplant.security.PassportHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PlantService implements PlantUseCase {

    private final PlantPort plantPort;

    @Autowired
    public PlantService(PlantPort plantPort) {
        this.plantPort = plantPort;
    }

    @Override
    public Long createPlant(PlantCommand command) {
        Character character = plantPort.findCharacterById(command.getCharacterId())
                .orElseThrow(() -> new RuntimeException("Character not found"));

        Long memberId = PassportHolder.getPassport().memberId();
        Plant plant = Plant.builder()
                .memberId(memberId)
                .plantName(command.getPlantName())
                .plantType(command.getPlantType())
                .plantAge(command.getPlantAge())
                .plantBirthday(command.getPlantBirthday())
                .lastWaterday(command.getLastWaterday())
                .wateringIntervalInDays(command.getWateringIntervalInDays())
                .plantLocation(command.getPlantLocation())
                .potType(command.getPotType())
                .intimacy(1) // 친밀도 초기값 1
                .character(character)
                .build();
        return plantPort.savePlant(plant).getId();
    }

    @Override
    public Long updatePlant(Long plantId, PlantCommand command) {
        Plant plant = plantPort.findPlantById(plantId)
                .orElseThrow(() -> new RuntimeException("Plant not found"));

        plant.setPlantName(command.getPlantName());
        plant.setPlantType(command.getPlantType());
        plant.setPlantAge(command.getPlantAge());
        plant.setPlantBirthday(command.getPlantBirthday());
        plant.setLastWaterday(command.getLastWaterday());
        plant.setPlantLocation(command.getPlantLocation());
        plant.setPotType(command.getPotType());
        plant.setWateringIntervalInDays(command.getWateringIntervalInDays());

        return plantPort.savePlant(plant).getId();
    }

    @Override
    public void deletePlant(Long plantId) {
        plantPort.deletePlantById(plantId);
    }

    @Override
    public List<Plant> getPlantsByMemberId(Long memberId) {
        return plantPort.findPlantsByMemberId(memberId);
    }

    @Override
    public Plant getPlantById(Long plantId) {
        return plantPort.findPlantById(plantId)
                .orElseThrow(() -> new RuntimeException("Plant not found"));
    }
}