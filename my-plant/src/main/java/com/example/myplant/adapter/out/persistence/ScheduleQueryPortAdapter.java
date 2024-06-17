package com.example.myplant.adapter.out.persistence;

import com.example.common.exception.NotFoundException;
import java.util.List;

import com.example.myplant.adapter.out.persistence.entity.ScheduleEntity;
import com.example.myplant.application.port.out.ScheduleQueryPort;
import com.example.myplant.domain.Schedule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
    public List<Schedule> findByMemberIdAndStatusOrderByEndDateTime(Long memberId, Boolean isCompleted) {
        return scheduleRepository.findByMemberIdAndIsCompletedOrderByEndDateTime(memberId, isCompleted).stream()
                .map(ScheduleEntity::toDomain).
                toList();
    }

}
