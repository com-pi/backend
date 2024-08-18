package com.example.myplant.application.port.in;

import com.example.myplant.adapter.in.web.command.GetDiaryScheduleCommand;
import com.example.myplant.adapter.in.web.response.ScheduleCalendarResponse;
import com.example.myplant.adapter.in.web.response.ScheduleMainResponseList;
import com.example.myplant.domain.Schedule;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleUseCase {

    Long createSchedule(Schedule schedule);

    Long updateSchedule(Schedule schedule);

    Long updateScheduleStatus(Schedule schedule);

    Long deleteSchedule(Schedule schedule);

    ScheduleMainResponseList getTodayScheduleList(Schedule schedule);

    ScheduleMainResponseList getUpcomingScheduleList(Schedule schedule);

    List<ScheduleCalendarResponse> getScheduleCalendarList(GetDiaryScheduleCommand command);

    ScheduleMainResponseList getScheduleByDate(LocalDate date, Schedule schedule);

}
