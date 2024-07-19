package com.example.myplant.application.port.out;

import com.example.myplant.domain.MyPlant;

import java.util.List;

public interface MyPlantQueryPort {

    List<MyPlant> getMyPlantListByMemberId(Long memberId);

    MyPlant getMyPlantByMyPlantId(Long myPlantId);

}
