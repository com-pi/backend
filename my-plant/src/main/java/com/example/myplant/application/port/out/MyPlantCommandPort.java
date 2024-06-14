package com.example.myplant.application.port.out;

import com.example.myplant.domain.MyPlant;

public interface MyPlantCommandPort {

    Long save(MyPlant myPlant);

    void update(MyPlant myPlant);

    void delete(Long myPlantId);
}
