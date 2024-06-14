package com.example.myplant.application.port.in;

import com.example.myplant.adapter.in.web.response.MyPlantDetailResponse;
import com.example.myplant.domain.MyPlant;

import java.util.List;

public interface MyPlantUseCase {

    Long createPlant(MyPlant myPlant);

    Long updateMyPlant(MyPlant myPlant);

    List<MyPlant> getMyPlantList(Long memberId);

    MyPlant getMyPlantByMyPlantId(Long myPlantId);
}
