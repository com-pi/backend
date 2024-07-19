package com.example.myplant.adapter.in.web.response;

import java.util.List;

public record ScheduleCalendarResponseList(
    List<ScheduleCalendarResponse> scheduleCalendarResponseList
) {
    public static ScheduleCalendarResponseList of(List<ScheduleCalendarResponse> scheduleCalendarResponseList) {
        return new ScheduleCalendarResponseList(scheduleCalendarResponseList);
    }
}
