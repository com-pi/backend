package com.example.myplant.adapter.out.persistence;

import com.example.common.exception.NotFoundException;
import com.example.myplant.adapter.in.web.command.GetDiaryScheduleCommand;
import com.example.myplant.adapter.out.persistence.entity.CompletedScheduleEntity;
import com.example.myplant.adapter.out.persistence.entity.ScheduleEntity;
import com.example.myplant.application.port.out.ScheduleQueryPort;
import com.example.myplant.domain.CompletedSchedule;
import com.example.myplant.domain.Schedule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ScheduleQueryPortAdapter implements ScheduleQueryPort {

    private final ScheduleRepository scheduleRepository;
    private final CompletedScheduleRepository completedScheduleRepository;

    @Override
    public Schedule findByScheduleId(Long scheduleId) {
        return scheduleRepository.findById(scheduleId)
            .orElseThrow(() -> new NotFoundException(ScheduleEntity.class))
            .toDomain();
    }

    @Override
    public List<Schedule> getTodayScheduleList(Long memberId) {
        return scheduleRepository.getTodayScheduleList(memberId).stream()
                .map(ScheduleEntity::toDomain)
                .toList();
    }

    @Override
    public List<Schedule> getRecurringScheduleList(Long memberId) {
        return scheduleRepository.getRecurringScheduleList(memberId).stream()
                .map(ScheduleEntity::toDomain)
                .toList();
    }

    @Override
    public List<Schedule> getRecurringScheduleCalendarList(LocalDate startDate, LocalDate endDate, Long memberId) {
        List<ScheduleEntity> scheduleEntityList = scheduleRepository.getRecurringScheduleCalendarList(startDate, endDate, memberId);
        return scheduleEntityList.stream()
                .map(ScheduleEntity::toDomain)
                .toList();
    }

    @Override
    public List<Schedule> getUpcomingRecurringScheduleList(LocalDate startDate, LocalDate endDate, Long memberId) {
        // @TODO isCompleted
        return scheduleRepository.getUpcomingRecurringScheduleList(startDate, endDate, memberId).stream()
                .map(ScheduleEntity::toDomain)
                .toList();
    }

    @Override
    public List<Schedule> getUpcomingScheduleList(LocalDate startDate, LocalDate endDate, Long memberId) {
        // @TODO isCompleted
        List<ScheduleEntity> scheduleEntityList = scheduleRepository.getUpcomingScheduleList(startDate, endDate, memberId);
        return scheduleEntityList.stream()
                .map(ScheduleEntity::toDomain)
                .collect(Collectors.toList());
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
    public List<Schedule> getSingleScheduleCalender(LocalDate date, Long memberId) {
        List<ScheduleEntity> scheduleEntityList = scheduleRepository.getSingleScheduleCalender(date, memberId);
        return scheduleEntityList.stream()
                .map(ScheduleEntity::toDomain)
                .toList();
    }

    @Override
    public List<CompletedSchedule> isCompleted(List<Long> scheduleIdList, LocalDate date) {
        List<CompletedScheduleEntity> completedScheduleEntityList =
                completedScheduleRepository.findBySchedule_ScheduleIdInAndCompletedDate(scheduleIdList, date);
        return completedScheduleEntityList.stream()
                .map(CompletedScheduleEntity::toDomain)
                .toList();
    }

}
