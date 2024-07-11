package com.example.myplant.application.service;

import com.example.common.exception.UnauthorizedException;
import com.example.myplant.application.port.in.ScheduleUseCase;
import com.example.myplant.application.port.out.ScheduleCommandPort;
import com.example.myplant.application.port.out.ScheduleQueryPort;
import com.example.myplant.adapter.in.web.response.ScheduleMainResponseList;
import com.example.myplant.domain.Schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleService implements ScheduleUseCase {

    private final ScheduleCommandPort scheduleCommandPort;
    private final ScheduleQueryPort scheduleQueryPort;

    @Override
    @Transactional
    public Long createSchedule(Schedule schedule) {
        schedule.checkEndDateTime();
        return scheduleCommandPort.createSchedule(schedule);
    }

    @Override
    @Transactional
    public Long updateSchedule(Schedule schedule) {
        schedule.checkEndDateTime();

        Schedule originSchedule = scheduleQueryPort.findByScheduleId(schedule.getScheduleId());
        if(!schedule.isWriter(originSchedule.getMemberId())) {
            throw new UnauthorizedException("일정을 수정할 권한이 없습니다.");
        }
        return scheduleCommandPort.updateSchedule(schedule);
    }

    @Override
    @Transactional
    public Long updateScheduleStatus(Schedule schedule) {
        Schedule originSchedule = scheduleQueryPort.findByScheduleId(schedule.getScheduleId());
        if(!schedule.isWriter(originSchedule.getMemberId())) {
            throw new UnauthorizedException("일정을 수정할 권한이 없습니다.");
        }

        return scheduleCommandPort.updateScheduleStatus(schedule, originSchedule.getIsCompleted());
    }

    @Override
    public ScheduleMainResponseList getMainPageScheduleList(Long memberId) {
        List<Schedule> scheduleList = scheduleQueryPort.getMainPageScheduleList(memberId, false);
        return ScheduleMainResponseList.from(scheduleList);
    }

    @Override
    public ScheduleMainResponseList getTodayScheduleList(Long memberId) {
        List<Schedule> scheduleList = scheduleQueryPort.getTodayScheduleList(memberId, false);
        return ScheduleMainResponseList.from(scheduleList);
    }

}
