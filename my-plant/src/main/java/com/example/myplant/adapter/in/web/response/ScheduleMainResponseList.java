package com.example.myplant.adapter.in.web.response;

import com.example.myplant.domain.Schedule;
import java.time.LocalDateTime;
import java.util.List;
public record ScheduleMainResponseList(
    List<ScheduleMainResponse> scheduleMainResponseList
) {

    public ScheduleMainResponseList(final List<ScheduleMainResponse> scheduleMainResponseList) {
        this.scheduleMainResponseList = scheduleMainResponseList;
    }

    public static ScheduleMainResponseList from(List<Schedule> scheduleList) {
        LocalDateTime now = LocalDateTime.now();
        List<ScheduleMainResponse> scheduleMainResponseList = scheduleList.stream()
            .map(row -> ScheduleMainResponse.from(now, row)).toList();
        return new ScheduleMainResponseList(scheduleMainResponseList);
    }

}
