package com.example.myplant.application.port.out;

import com.example.myplant.domain.Plant;

public interface SavePlantPort {
    Plant savePlant(Plant plant);
}
