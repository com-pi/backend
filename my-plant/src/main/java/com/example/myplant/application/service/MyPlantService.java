package com.example.myplant.application.service;

import com.example.myplant.adapter.in.web.response.MyPlantDetailResponse;
import com.example.myplant.application.port.in.MyPlantUseCase;
import com.example.myplant.application.port.out.MyPlantCommandPort;
import com.example.myplant.application.port.out.MyPlantQueryPort;
import com.example.myplant.domain.MyPlant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyPlantService implements MyPlantUseCase {

    private final MyPlantCommandPort myPlantCommandPort;
    private final MyPlantQueryPort myPlantQueryPort;

    @Override
    @Transactional
    public Long createPlant(MyPlant myPlant) {
        return myPlantCommandPort.save(myPlant).getMyPlantId();
    }

    @Override
    public List<MyPlant> getMyPlantList(Long memberId) {
        return myPlantQueryPort.getMyPlantListByMemberId(memberId);
    }

    @Override
    public MyPlant getMyPlantByMyPlantId(Long myPlantId) {
        return myPlantQueryPort.getMyPlantByMyPlantId(myPlantId);
    }
}