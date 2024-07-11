package com.example.myplant.adapter.out.persistence;

import com.example.common.exception.NotFoundException;
import com.example.myplant.adapter.out.persistence.entity.ScheduleEntity;
import com.example.myplant.application.port.out.ScheduleQueryPort;
import com.example.myplant.domain.Schedule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ScheduleQueryPortAdapter implements ScheduleQueryPort {

    private final ScheduleRepository scheduleRepository;

    @Override
    public Schedule findByScheduleId(Long scheduleId) {
        return scheduleRepository.findById(scheduleId)
            .orElseThrow(() -> new NotFoundException(ScheduleEntity.class))
            .toDomain();
    }
    @Override
    public List<Schedule> getMainPageScheduleList(Long memberId, Boolean isCompleted) {
        return scheduleRepository.findByMemberIdAndIsCompletedOrderByEndDateTime(memberId, isCompleted).stream()
                .map(ScheduleEntity::toDomain).
                toList();
    }

    @Override
    public List<Schedule> getTodayScheduleList(Long memberId, boolean isCompleted) {
        return scheduleRepository.getTodayScheduleList(memberId, isCompleted).stream()
                .map(ScheduleEntity::toDomain)
                .toList();
    }

}
