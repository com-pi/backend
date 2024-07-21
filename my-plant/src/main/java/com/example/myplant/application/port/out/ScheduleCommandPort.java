package com.example.myplant.application.port.out;

import com.example.myplant.domain.Schedule;

public interface ScheduleCommandPort {

    Long createSchedule(Schedule schedule);

    Long updateSchedule(Schedule schedule);

    Long updateScheduleStatus(Schedule schedule);
}
