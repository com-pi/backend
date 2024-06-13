package com.example.myplant.adapter.out.persistence;

import com.example.myplant.application.port.out.MyPlantQueryPort;
import com.example.myplant.domain.MyPlant;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class MyPlantQueryAdapter implements MyPlantQueryPort {

    private final MyPlantRepository myPlantRepository;

    @Override
    public List<MyPlant> getMyPlantListByMemberId(Long memberId) {

        return null;
    }
}
