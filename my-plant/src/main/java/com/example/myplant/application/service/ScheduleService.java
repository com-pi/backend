package com.example.myplant.application.service;

import com.example.myplant.adapter.in.web.command.GetDiaryScheduleCommand;
import com.example.myplant.adapter.in.web.response.ScheduleCalendarResponse;
import com.example.myplant.adapter.in.web.response.ScheduleMainResponseList;
import com.example.myplant.application.port.in.ScheduleUseCase;
import com.example.myplant.application.port.out.ScheduleCommandPort;
import com.example.myplant.application.port.out.ScheduleQueryPort;
import com.example.myplant.domain.CompletedSchedule;
import com.example.myplant.domain.Schedule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleService implements ScheduleUseCase {

    private final ScheduleCommandPort scheduleCommandPort;
    private final ScheduleQueryPort scheduleQueryPort;

    @Override
    @Transactional
    public Long createSchedule(Schedule schedule) {
        schedule.validateEndDateTime();
        return scheduleCommandPort.createSchedule(schedule);
    }

    @Override
    @Transactional
    public Long updateSchedule(Schedule schedule) {
        Schedule originSchedule = scheduleQueryPort.findByScheduleId(schedule.getScheduleId());
        schedule.validateEndDateTime();
        schedule.validateWriter(originSchedule, schedule);
        return scheduleCommandPort.updateSchedule(schedule);
    }

    @Override
    @Transactional
    public Long updateScheduleStatus(Schedule schedule) {
        Schedule originSchedule = scheduleQueryPort.findByScheduleId(schedule.getScheduleId());
        schedule.validateWriter(originSchedule, schedule);
        schedule.validateScheduleCompletionDate(originSchedule, LocalDate.now());
        return scheduleCommandPort.updateScheduleStatus(schedule);
    }

    @Override
    public ScheduleMainResponseList getTodayScheduleList(Schedule schedule) {
        // 단건 일정
        List<Schedule> scheduleList = scheduleQueryPort.getTodayScheduleList(schedule.getMemberId());

        // 반복 일정
        List<Schedule> recurringScheduleList = scheduleQueryPort.getRecurringScheduleList(schedule.getMemberId());
        List<Schedule> matchingSchedule = schedule.findMatchingSchedules(recurringScheduleList, LocalDate.now());

        return ScheduleMainResponseList.from(schedule.getMatchingScheduleList(scheduleList, matchingSchedule));
    }

    @Override
    public ScheduleMainResponseList getUpcomingScheduleList(Schedule domain) {
        LocalDate today = LocalDate.now();
        LocalDate weekLater = today.plusDays(7);
        Long memberId = domain.getMemberId();

        // 단건 일정
        List<Schedule> singleScheduleList = scheduleQueryPort.getUpcomingScheduleList(today, weekLater, memberId);

        // 반복 일정
        List<Schedule> recurringScheduleList = scheduleQueryPort.getUpcomingRecurringScheduleList(today, weekLater, memberId)
                .stream()
                .flatMap(schedule -> schedule.getRecurringSchedule(schedule, today, weekLater).stream())
                .toList();

        return ScheduleMainResponseList.from(domain.getMatchingScheduleList(singleScheduleList, recurringScheduleList));
    }

    @Override
    public List<ScheduleCalendarResponse> getScheduleCalendarList(GetDiaryScheduleCommand command) {
        // 단건 일정
        List<Schedule> scheduleList = scheduleQueryPort.getScheduleCalendarList(command);
        List<ScheduleCalendarResponse> scheduleResponseList = scheduleList.stream()
                .map(schedule -> ScheduleCalendarResponse.from(schedule.getStartDateTime().toLocalDate(), schedule.getColorType()))
                .toList();

        // 반복 일정
        List<Schedule> recurringScheduleList = scheduleQueryPort.getRecurringScheduleCalendarList(
                command.getStartDate(),
                command.getEndDate(),
                command.getMemberId()
        );
        List<ScheduleCalendarResponse> recurringScheduleResponseList = recurringScheduleList.stream()
                .flatMap(schedule -> schedule.getRecurringDate(schedule, command.getStartDate(), command.getEndDate()).stream()
                .map(date -> ScheduleCalendarResponse.from(date, schedule.getColorType())))
                .toList();

        return Stream
                .concat(scheduleResponseList.stream(), recurringScheduleResponseList.stream())
                .toList();
    }

    @Override
    public ScheduleMainResponseList getScheduleByDate(LocalDate date, Schedule schedule) {
        // 단건 일정
        List<Schedule> scheduleList = scheduleQueryPort.getSingleScheduleCalender(date, schedule.getMemberId());
        setStatus(scheduleList, date);

        // 반복 일정
        List<Schedule> recurringScheduleList = scheduleQueryPort.getRecurringScheduleCalendarList(date, date, schedule.getMemberId());
        List<Schedule> filteredScheduleList = schedule.findMatchingSchedules(recurringScheduleList, date);
        setStatus(filteredScheduleList, date);

        List<Schedule> matchingScheduleList = schedule.getMatchingScheduleList(scheduleList, filteredScheduleList);
        return ScheduleMainResponseList.from(matchingScheduleList);
    }

    /**
     * private
     */
    private void setStatus(List<Schedule> scheduleList, LocalDate date) {
        List<Long> scheduleIdList = scheduleList.stream()
                .map(Schedule::getScheduleId)
                .toList();

        List<CompletedSchedule> completedScheduleList = scheduleQueryPort.isCompleted(scheduleIdList, date);
        Map<Long, Boolean> completedScheduleMap = completedScheduleList.stream()
                .collect(Collectors.toMap(
                        CompletedSchedule::getScheduleId,
                        completedSchedule -> true
                ));
        scheduleList.forEach(s -> {
            boolean isCompleted = completedScheduleMap.getOrDefault(s.getScheduleId(), false);
            s.setStatus(isCompleted);
        });
    }

}
