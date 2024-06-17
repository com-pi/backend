package com.example.myplant.application.port.out;

import com.example.myplant.domain.Schedule;

import java.util.List;

public interface ScheduleQueryPort {

    Schedule findByScheduleId(Long scheduleId);

    List<Schedule> findByMemberIdAndStatusOrderByEndDateTime(Long memberId, Boolean isCompleted);

}
