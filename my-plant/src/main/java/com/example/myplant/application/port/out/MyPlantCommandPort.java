package com.example.myplant.application.port.out;

import com.example.myplant.domain.MyPlant;

public interface MyPlantCommandPort {

    MyPlant save(MyPlant myPlant);

}
