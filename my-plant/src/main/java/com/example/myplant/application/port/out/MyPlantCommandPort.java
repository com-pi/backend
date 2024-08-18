package com.example.myplant.application.port.out;

import com.example.myplant.domain.MyPlant;

public interface MyPlantCommandPort {

    MyPlant save(MyPlant myPlant);

    void update(MyPlant myPlant);

    void delete(Long myPlantId);

    void updatePlantCharacter(MyPlant myPlant);
}
