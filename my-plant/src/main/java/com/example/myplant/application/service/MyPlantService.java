package com.example.myplant.application.service;

import com.example.myplant.application.port.in.MyPlantUseCase;
import com.example.myplant.application.port.out.MyPlantCommandPort;
import com.example.myplant.application.port.out.MyPlantQueryPort;
import com.example.myplant.domain.MyPlant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyPlantService implements MyPlantUseCase {

    private final MyPlantCommandPort myPlantCommandPort;
    private final MyPlantQueryPort myPlantQueryPort;

    @Override
    @Transactional
    public Long createPlant(MyPlant myPlant) {
        return myPlantCommandPort.save(myPlant).getMyPlantId();
    }

    @Override
    public List<MyPlant> getMyPlantList() {
        return null;
    }

//    @Override
//    public Long updatePlant(Long plantId, PlantCommand command) {
//        Plant plant = plantPort.findPlantById(plantId)
//                .orElseThrow(() -> new RuntimeException("Plant not found"));
//
//        plant.setPlantName(command.getPlantName());
//        plant.setPlantType(command.getPlantType());
//        plant.setPlantAge(command.getPlantAge());
//        plant.setPlantBirthday(command.getPlantBirthday());
//        plant.setLastWaterday(command.getLastWaterday());
//        plant.setPlantLocation(command.getPlantLocation());
//        plant.setPotType(command.getPotType());
//        plant.setWateringIntervalInDays(command.getWateringIntervalInDays());
//
//        return plantPort.savePlant(plant).getId();
//    }
//
//    @Override
//    public void deletePlant(Long plantId) {
//        plantPort.deletePlantById(plantId);
//    }
//
//    @Override
//    public List<Plant> getPlantsByMemberId(Long memberId) {
//        return plantPort.findPlantsByMemberId(memberId);
//    }
//
//    @Override
//    public Plant getPlantById(Long plantId) {
//        return plantPort.findPlantById(plantId)
//                .orElseThrow(() -> new RuntimeException("Plant not found"));
//    }
}