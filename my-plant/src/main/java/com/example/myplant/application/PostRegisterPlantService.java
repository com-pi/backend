package com.example.myplant.application;

import com.example.myplant.application.port.in.RegisterPlantCommand;
import com.example.myplant.application.port.in.PostPlantUseCase;
import com.example.myplant.application.port.out.PostRegistPlantPort;
import com.example.myplant.domain.Plant;
import com.example.myplant.domain.PlantLocation;
import com.example.myplant.domain.PlantStatus;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service

@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostRegisterPlantService implements PostPlantUseCase {

    private final PostRegistPlantPort postRegistPlantPort;

    @Override
    @Transactional
    public void postRegisterPlant(RegisterPlantCommand command) {
        Plant newPlant = Plant.generate()
                .id(command.getId())
                .memberId(command.getMemberId())
                .plantName(command.getPlantName())
                .plantType(command.getPlantType())
                .plantAge(command.getPlantAge())
                .plantBirthday(command.getPlantBirthday())
                .plantImageUrl(command.getPlantImageUrl())
                .plantWaterDays(command.getPlantWaterDays())
                .lastWaterDay(command.getLastWaterDay())
                .plantLocation(PlantLocation.of(String.valueOf(command.getPlantLocation())))
                .plantStatus(PlantStatus.Alive)
                .build();

        postRegistPlantPort.postRegistPlant(newPlant);
    }

}
