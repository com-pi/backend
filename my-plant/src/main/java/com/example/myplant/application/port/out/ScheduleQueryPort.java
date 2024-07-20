package com.example.myplant.application.port.out;

import com.example.myplant.adapter.in.web.command.GetDiaryScheduleCommand;
import com.example.myplant.domain.Schedule;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleQueryPort {

    Schedule findByScheduleId(Long scheduleId);

    List<Schedule> getMainPageScheduleList(Long memberId, Boolean isCompleted);

    List<Schedule> getTodayScheduleList(Long memberId, boolean isCompleted);

    List<Schedule> getUpcomingScheduleList(LocalDate startDate, LocalDate endDate, Long memberId);

    List<Schedule> getUpcomingRecurringScheduleList(LocalDate startDate, LocalDate endDate, Long memberId);

    List<Schedule> getScheduleList(Long memberId);

    List<Schedule> getRecurringScheduleList(Long memberId, boolean isCompleted);

    List<Schedule> getRecurringScheduleCalendarList(LocalDate startDate, LocalDate endDate, Long memberId);

    List<Schedule> getScheduleCalendarList(GetDiaryScheduleCommand command);

    List<Schedule> getSingleScheduleCalender(LocalDate date, Long memberId);

}
