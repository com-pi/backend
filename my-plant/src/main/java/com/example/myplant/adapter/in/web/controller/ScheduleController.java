package com.example.myplant.adapter.in.web.controller;

import com.example.common.annotation.Authenticate;
import com.example.common.baseentity.CommonResponse;
import com.example.common.domain.Role;
import com.example.myplant.adapter.in.web.command.UpdateScheduleStatusCommand;
import com.example.myplant.adapter.in.web.request.UpdateScheduleRequest;
import com.example.myplant.application.port.in.ScheduleUseCase;
import com.example.myplant.adapter.in.web.request.CreateScheduleRequest;
import com.example.myplant.adapter.in.web.response.ScheduleMainResponseList;
import com.example.myplant.security.PassportHolder;
import jakarta.ws.rs.Path;
import lombok.RequiredArgsConstructor;
import org.aspectj.apache.bcel.classfile.LineNumber;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @PatchMapping("/status/{scheduleId}")
    public ResponseEntity<CommonResponse<Long>> updateScheduleStatus(@PathVariable Long scheduleId) {
        UpdateScheduleStatusCommand command = UpdateScheduleStatusCommand.of(scheduleId, PassportHolder.getPassport().memberId());
        Long id = scheduleUseCase.updateScheduleStatus(command.toDomain());
        return ResponseEntity.ok(new CommonResponse<>("일정의 상태가 수정되었습니다.", id));
    }


    @Override
    @Authenticate(Role.MEMBER)
    @GetMapping("/main")
    public ResponseEntity<ScheduleMainResponseList> getMainPageScheduleList() {
        ScheduleMainResponseList scheduleMainResponseList = scheduleUseCase.getMainPageScheduleList(PassportHolder.getPassport().memberId());
        return ResponseEntity.ok(scheduleMainResponseList);
    }
}