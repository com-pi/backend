package com.example.myplant.application.service;

import com.example.common.exception.UnauthorizedException;
import com.example.myplant.application.port.in.MyPlantUseCase;
import com.example.myplant.application.port.out.MyPlantCommandPort;
import com.example.myplant.application.port.out.MyPlantQueryPort;
import com.example.myplant.domain.MyPlant;
import com.example.myplant.domain.Schedule;
import com.example.myplant.event.MyPlantEventPublisher;
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
    private final MyPlantEventPublisher eventPublisher;

    @Override
    @Transactional
    public Long createPlant(MyPlant myPlant) {
        MyPlant savedmyPlant = myPlantCommandPort.save(myPlant);

        /* 식물-일정 연동 */
        eventPublisher.publishPlantSchedule(Schedule.from(myPlant));
        return savedmyPlant.getMyPlantId();
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
    public Long deleteMyPlant(Long memberId, Long myPlantId) {
        myPlantCommandPort.delete(myPlantId);
        return myPlantId;
    }

    @Override
    public List<MyPlant> getMyPlantList(Long memberId) {
        return myPlantQueryPort.getMyPlantListByMemberId(memberId);
    }

    @Override
    public MyPlant getMyPlant(Long myPlantId) {
        return myPlantQueryPort.getMyPlantByMyPlantId(myPlantId);
    }

    @Override
    @Transactional
    public Long updatePlantCharacter(MyPlant myPlant) {
        MyPlant originMyPlant = myPlantQueryPort.getMyPlantByMyPlantId(myPlant.getMyPlantId());
        myPlant.validatePermission(originMyPlant);
        myPlantCommandPort.updatePlantCharacter(myPlant);
        return myPlant.getMyPlantId();
    }

    /**
     * private
     */
    private void validatePermission(final Long originMemberId, final Long memberId) {
        if(!Objects.equals(originMemberId, memberId)) {
            throw new UnauthorizedException("식물을 수정하거나 삭제할 권한이 없습니다.");
        }
    }
}