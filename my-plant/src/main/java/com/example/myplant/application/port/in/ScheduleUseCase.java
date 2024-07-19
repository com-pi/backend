package com.example.myplant.application.port.in;

import com.example.myplant.adapter.in.web.command.GetDiaryScheduleCommand;
import com.example.myplant.adapter.in.web.response.ScheduleMainResponseList;
import com.example.myplant.domain.Schedule;

public interface ScheduleUseCase {

    Long createSchedule(Schedule schedule);

    Long updateSchedule(Schedule schedule);

    Long updateScheduleStatus(Schedule schedule);

    ScheduleMainResponseList getMainPageScheduleList(Long memberId);

    ScheduleMainResponseList getTodayScheduleList(Schedule schedule);

    ScheduleMainResponseList getUpcomingScheduleList(Schedule schedule);

    void getScheduleCalendarList(GetDiaryScheduleCommand command);
}
