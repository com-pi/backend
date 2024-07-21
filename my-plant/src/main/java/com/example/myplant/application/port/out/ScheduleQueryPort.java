package com.example.myplant.application.port.out;

import com.example.myplant.adapter.in.web.command.GetDiaryScheduleCommand;
import com.example.myplant.domain.CompletedSchedule;
import com.example.myplant.domain.Schedule;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleQueryPort {

    Schedule findByScheduleId(Long scheduleId);

    List<Schedule> getTodayScheduleList(Long memberId);

    List<Schedule> getUpcomingScheduleList(LocalDate startDate, LocalDate endDate, Long memberId);

    List<Schedule> getUpcomingRecurringScheduleList(LocalDate startDate, LocalDate endDate, Long memberId);

    List<Schedule> getRecurringScheduleList(Long memberId);

    List<Schedule> getRecurringScheduleCalendarList(LocalDate startDate, LocalDate endDate, Long memberId);

    List<Schedule> getScheduleCalendarList(GetDiaryScheduleCommand command);

    List<Schedule> getSingleScheduleCalender(LocalDate date, Long memberId);

    List<CompletedSchedule> isCompleted(List<Long> schedule, LocalDate date);
}
