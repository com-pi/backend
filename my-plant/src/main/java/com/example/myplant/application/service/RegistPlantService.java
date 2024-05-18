package com.example.myplant.application.service;

import com.example.myplant.adapter.in.web.PlantCommand;
import com.example.myplant.application.port.in.RegistPlantUseCase;
import com.example.myplant.application.port.out.SavePlantPort;
import com.example.myplant.application.port.out.ImageUploadPort;
import com.example.myplant.domain.Plant;
import com.example.myplant.domain.WateringInfo;
import com.example.myplant.domain.MaintenanceSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistPlantService implements RegistPlantUseCase {

    private final SavePlantPort savePlantPort;
    private final ImageUploadPort imageUploadPort;

    @Autowired
    public RegistPlantService(SavePlantPort savePlantPort, ImageUploadPort imageUploadPort) {
        this.savePlantPort = savePlantPort;
        this.imageUploadPort = imageUploadPort;
    }

    @Override
    public Plant registerPlant(PlantCommand command) {
        List<String> imageUrls = imageUploadPort.uploadImages(command.getPlantImages());

        WateringInfo wateringInfo = WateringInfo.builder()
                .intervalInWeeks(command.getWateringIntervalInWeeks())
                .frequency(command.getWateringFrequency())
                .build();

        MaintenanceSchedule maintenanceSchedule = MaintenanceSchedule.builder()
                .repottingDate(command.getRepottingDate())
                .fertilizingDate(command.getFertilizingDate())
                .pruningDate(command.getPruningDate())
                .build();

        Plant plant = Plant.builder()
                .memberId(command.getMemberId())
                .plantName(command.getPlantName())
                .plantType(command.getPlantType())
                .plantAge(command.getPlantAge())
                .plantBirthday(command.getPlantBirthday())
                .lastWaterday(command.getLastWaterday())
                .plantImageUrls(imageUrls)
                .wateringInfo(wateringInfo)
                .maintenanceSchedule(maintenanceSchedule)
                .plantLocation(command.getPlantLocation())
                .potType(command.getPotType())
                .intimacy(1) // 친밀도 초기값 1
                .build();

        return savePlantPort.savePlant(plant);
    }
}