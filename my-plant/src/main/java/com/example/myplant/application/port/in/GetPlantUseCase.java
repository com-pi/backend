package com.example.myplant.application.port.in;

import com.example.myplant.domain.Plant;

import java.util.List;

public interface GetPlantUseCase {
    List<Plant> getPlantsByMemberId(Long memberId);
    Plant getPlantByPlantId(Long plantId);
}
