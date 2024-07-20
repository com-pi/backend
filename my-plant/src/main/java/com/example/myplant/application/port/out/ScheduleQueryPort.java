package com.example.myplant.application.port.out;

import com.example.myplant.adapter.in.web.command.GetDiaryScheduleCommand;
import com.example.myplant.domain.Schedule;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleQueryPort {

    Schedule findByScheduleId(Long scheduleId);

    List<Schedule> getMainPageScheduleList(Long memberId, Boolean isCompleted);

    List<Schedule> getTodayScheduleList(Long memberId, boolean isCompleted);

    List<Schedule> getUpcomingScheduleList(LocalDateTime upcomingDate, Long memberId, boolean isCompleted);

    List<Schedule> getScheduleList(Long memberId);

    List<Schedule> getRecurringScheduleList(Long memberId, boolean isCompleted);

    List<Schedule> getRecurringScheduleCalendarList(GetDiaryScheduleCommand command);

    List<Schedule> getScheduleCalendarList(GetDiaryScheduleCommand command);
}
