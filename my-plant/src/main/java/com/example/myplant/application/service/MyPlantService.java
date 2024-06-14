package com.example.myplant.application.service;

import com.example.common.exception.UnauthorizedException;
import com.example.myplant.application.port.in.MyPlantUseCase;
import com.example.myplant.application.port.out.MyPlantCommandPort;
import com.example.myplant.application.port.out.MyPlantQueryPort;
import com.example.myplant.domain.MyPlant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyPlantService implements MyPlantUseCase {

    private final MyPlantCommandPort myPlantCommandPort;
    private final MyPlantQueryPort myPlantQueryPort;

    @Override
    @Transactional
    public Long createPlant(MyPlant myPlant) {
        return myPlantCommandPort.save(myPlant);
    }

    @Override
    @Transactional
    public Long updateMyPlant(MyPlant myPlant) {
        MyPlant originMyPlant = myPlantQueryPort.getMyPlantByMyPlantId(myPlant.getMyPlantId());
        validatePermission(originMyPlant.getMemberId(),  myPlant.getMemberId());
        myPlantCommandPort.update(myPlant);
        return myPlant.getMyPlantId();
    }

    @Override
    @Transactional
    public Long deletePlant(Long memberId, Long myPlantId) {
        myPlantCommandPort.delete(myPlantId);
        return myPlantId;
    }

    @Override
    public List<MyPlant> getMyPlantList(Long memberId) {
        return myPlantQueryPort.getMyPlantListByMemberId(memberId);
    }

    @Override
    public MyPlant getMyPlantByMyPlantId(Long myPlantId) {
        return myPlantQueryPort.getMyPlantByMyPlantId(myPlantId);
    }

    private void validatePermission(final Long originMemberId, final Long memberId) {
        if(!Objects.equals(originMemberId, memberId)) {
            throw new UnauthorizedException("식물을 수정하거나 삭제할 권한이 없습니다.");
        }
    }
}