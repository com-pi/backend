package com.example.myplant.adapter.out.persistence;

import com.example.myplant.application.port.out.PostRegistPlantPort;
import com.example.myplant.domain.Plant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlantCommandAdapter implements PostRegistPlantPort {

    private final RegisterPlantRepository registerPlantRepository;

    @Override
    public void postRegistPlant(Plant plant) {registerPlantRepository.save(plant);}
}
