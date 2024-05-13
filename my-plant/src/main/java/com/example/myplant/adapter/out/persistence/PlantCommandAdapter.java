package com.example.myplant.adapter.out.persistence;

import com.example.myplant.application.port.out.RegistPlantPort;
import com.example.myplant.domain.Plant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlantCommandAdapter implements RegistPlantPort {

    private final RegistPlantRepository registPlantRepository;

    @Override
    public void registPlant(Plant plant) {
        registPlantRepository.save(plant);}
}
