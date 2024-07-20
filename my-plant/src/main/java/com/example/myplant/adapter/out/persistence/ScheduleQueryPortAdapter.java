package com.example.myplant.adapter.out.persistence;

import com.example.common.exception.NotFoundException;
import com.example.myplant.adapter.in.web.command.GetDiaryScheduleCommand;
import com.example.myplant.adapter.out.persistence.entity.ScheduleEntity;
import com.example.myplant.application.port.out.ScheduleQueryPort;
import com.example.myplant.domain.Schedule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
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

    @Override
    public List<Schedule> getRecurringScheduleList(Long memberId, boolean isCompleted) {
        return scheduleRepository.getRecurringScheduleList(memberId, isCompleted).stream()
                .map(ScheduleEntity::toDomain)
                .toList();
    }

    @Override
    public List<Schedule> getRecurringScheduleCalendarList(GetDiaryScheduleCommand command) {
        List<ScheduleEntity> scheduleEntityList = scheduleRepository.getRecurringScheduleCalendarList(
                command.getStartDate(),
                command.getEndDate(),
                command.getMemberId()
        );
        return scheduleEntityList.stream()
                .map(ScheduleEntity::toDomain)
                .toList();
    }

    @Override
    public List<Schedule> getUpcomingScheduleList(LocalDateTime upcomingDate, Long memberId, boolean isCompleted) {
        return scheduleRepository.getUpcomingScheduleList(upcomingDate, memberId, isCompleted).stream()
                .map(ScheduleEntity::toDomain)
                .toList();
    }

    @Override
    public List<Schedule> getScheduleCalendarList(GetDiaryScheduleCommand command) {
        List<ScheduleEntity> scheduleEntityList = scheduleRepository.getScheduleCalendarList(
                command.getStartDate(),
                command.getEndDate(),
                command.getMemberId()
        );
        return scheduleEntityList.stream()
                .map(ScheduleEntity::toDomain)
                .toList();
    }

    @Override
    public List<Schedule> getScheduleList(Long memberId) {
        return scheduleRepository.findByMemberIdAndIsCompletedOrderByEndDateTime(memberId, false).stream()
                .map(ScheduleEntity::toDomain)
                .toList();
    }

}
