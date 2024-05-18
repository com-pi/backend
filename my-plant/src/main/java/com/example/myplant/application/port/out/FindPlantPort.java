package com.example.myplant.application.port.out;

import com.example.myplant.domain.Plant;
import java.util.Optional;

public interface FindPlantPort {
    Optional<Plant> findPlantById(Long id);
}
