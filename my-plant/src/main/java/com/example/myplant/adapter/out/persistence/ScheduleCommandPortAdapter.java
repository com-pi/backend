package com.example.myplant.adapter.out.persistence;

import com.example.common.exception.NotFoundException;
import com.example.myplant.adapter.out.persistence.entity.CompletedScheduleEntity;
import com.example.myplant.adapter.out.persistence.entity.ScheduleEntity;
import com.example.myplant.application.port.out.ScheduleCommandPort;
import com.example.myplant.domain.Schedule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class ScheduleCommandPortAdapter implements ScheduleCommandPort {

    private final ScheduleRepository scheduleRepository;
    private final CompletedScheduleRepository completedScheduleRepository;

    @Override
    public Long createSchedule(final Schedule schedule) {
        return scheduleRepository.save(ScheduleEntity.from(schedule))
            .getScheduleId();
    }
    @Override
    public Long updateSchedule(final Schedule schedule) {
        ScheduleEntity scheduleEntity = scheduleRepository.findById(schedule.getScheduleId())
                .orElseThrow(() -> new NotFoundException(ScheduleEntity.class));
        scheduleEntity.update(schedule);
        return scheduleEntity.getScheduleId();
    }

    @Override
    public Long updateScheduleStatus(final Schedule schedule) {
        ScheduleEntity scheduleEntity = scheduleRepository.findById(schedule.getScheduleId())
                .orElseThrow(() -> new NotFoundException(ScheduleEntity.class));

        completedScheduleRepository
            .findBySchedule_ScheduleIdAndCompletedDate(scheduleEntity.getScheduleId(), LocalDate.now())
            .ifPresentOrElse(
                completedScheduleEntity -> completedScheduleRepository.deleteById(completedScheduleEntity.getCompletedScheduleId()),
                () -> completedScheduleRepository.save(CompletedScheduleEntity.of(scheduleEntity, LocalDate.now()))
            );

        return scheduleEntity.getScheduleId();
    }

}
