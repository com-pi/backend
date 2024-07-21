package com.example.myplant.adapter.in.web.controller;

import com.example.common.annotation.Authenticate;
import com.example.common.baseentity.CommonResponse;
import com.example.common.domain.Role;
import com.example.myplant.adapter.in.web.command.GetDiaryScheduleCommand;
import com.example.myplant.adapter.in.web.command.GetScheduleCommand;
import com.example.myplant.adapter.in.web.command.UpdateScheduleStatusCommand;
import com.example.myplant.adapter.in.web.request.CreateScheduleRequest;
import com.example.myplant.adapter.in.web.request.UpdateScheduleRequest;
import com.example.myplant.adapter.in.web.response.ScheduleCalendarResponse;
import com.example.myplant.adapter.in.web.response.ScheduleCalendarResponseList;
import com.example.myplant.adapter.in.web.response.ScheduleMainResponseList;
import com.example.myplant.application.port.in.ScheduleUseCase;
import com.example.myplant.security.PassportHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController implements ScheduleSwaggerUI {

    private final ScheduleUseCase scheduleUseCase;

    @Override
    @Authenticate(Role.MEMBER)
    @PostMapping
    public ResponseEntity<CommonResponse<Long>> createSchedule(@RequestBody CreateScheduleRequest request) {
        Long id = scheduleUseCase.createSchedule(request.toDomain(PassportHolder.getPassport().memberId()));
        return ResponseEntity.ok(new CommonResponse<>("일정이 등록되었습니다.", id));
    }

    @Override
    @Authenticate(Role.MEMBER)
    @PatchMapping("/{scheduleId}")
    public ResponseEntity<CommonResponse<Long>> updateSchedule(@PathVariable Long scheduleId, @RequestBody UpdateScheduleRequest request) {
        Long id = scheduleUseCase.updateSchedule(request.toDomain(scheduleId, PassportHolder.getPassport().memberId()));
        return ResponseEntity.ok(new CommonResponse<>("일정이 수정되었습니다.", id));
    }

    @Override
    @Authenticate(Role.MEMBER)
    @PatchMapping("/status/{scheduleId}")
    public ResponseEntity<CommonResponse<Long>> updateScheduleStatus(@PathVariable Long scheduleId) {
        UpdateScheduleStatusCommand command = UpdateScheduleStatusCommand.of(scheduleId, PassportHolder.getPassport().memberId());
        Long id = scheduleUseCase.updateScheduleStatus(command.toDomain());
        return ResponseEntity.ok(new CommonResponse<>("일정의 상태가 수정되었습니다.", id));
    }

    @Override
    @Authenticate(Role.MEMBER)
    @GetMapping("/main/today")
    public ResponseEntity<ScheduleMainResponseList> getTodayScheduleList() {
        GetScheduleCommand command = GetScheduleCommand.of(PassportHolder.getPassport().memberId());
        ScheduleMainResponseList responseList = scheduleUseCase.getTodayScheduleList(command.toDomain());
        return ResponseEntity.ok(responseList);
    }

    @Override
    @Authenticate(Role.MEMBER)
    @GetMapping("/main/upcoming")
    public ResponseEntity<ScheduleMainResponseList> getUpcomingScheduleList() {
        GetScheduleCommand command = GetScheduleCommand.of(PassportHolder.getPassport().memberId());
        ScheduleMainResponseList responseList = scheduleUseCase.getUpcomingScheduleList(command.toDomain());
        return ResponseEntity.ok(responseList);
    }

    @Override
    @Authenticate(Role.MEMBER)
    @GetMapping("/calendar/list")
    public ResponseEntity<ScheduleCalendarResponseList> getScheduleCalendarList(
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate
    ) {
        List<ScheduleCalendarResponse> responseList = scheduleUseCase.getScheduleCalendarList(GetDiaryScheduleCommand.of(startDate, endDate, (PassportHolder.getPassport().memberId())));
        return ResponseEntity.ok(ScheduleCalendarResponseList.of(responseList));
    }

    @Override
    @Authenticate(Role.MEMBER)
    @GetMapping("/calendar/date")
    public ResponseEntity<ScheduleMainResponseList> getScheduleByDate(@RequestParam("date") final LocalDate date) {
        ScheduleMainResponseList scheduleMainResponseList = scheduleUseCase.getScheduleByDate(date, GetScheduleCommand.of(PassportHolder.getPassport().memberId()).toDomain());
        return ResponseEntity.ok(scheduleMainResponseList);
    }


}