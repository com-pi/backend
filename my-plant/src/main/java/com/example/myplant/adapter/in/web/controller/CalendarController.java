package com.example.myplant.adapter.in.web.controller;

import com.example.common.annotation.Authenticate;
import com.example.common.domain.Role;
import com.example.myplant.domain.Calendar;
import com.example.myplant.application.service.CalendarService;
import com.example.myplant.adapter.in.web.command.CalendarCommand;
import com.example.common.baseentity.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/calendar")
@Tag(name = "일정 관리", description = "일정 추가 및 수정 API")
public class CalendarController {
    private final CalendarService calendarService;

    @Autowired
    public CalendarController(CalendarService  calendarService) {
        this.calendarService = calendarService;
    }

    @Authenticate(Role.MEMBER)
    @PostMapping("/create")
    @Operation(summary = "일정 추가", description = "새로운 일정을 추가합니다.")
    public ResponseEntity<CommonResponse<Long>> createCalendar(@RequestBody CalendarCommand command) {
        Long calendarId = calendarService.createCalendar(command);
        return ResponseEntity.ok(new CommonResponse<>("Calendar added successfully", calendarId));
    }

    @Authenticate(Role.MEMBER)
    @PutMapping("/update/{id}")
    @Operation(summary = "일정 수정", description = "기존 일정를 수정합니다.")
    public ResponseEntity<CommonResponse<Long>> updateCalendar(@PathVariable Long id, @RequestBody CalendarCommand  command) {
        calendarService.updateCalendar(id, command);
        return ResponseEntity.ok(new CommonResponse<>("Calendar updated successfully", id));
    }

    @Authenticate(Role.MEMBER)
    @GetMapping("/plant/{plantId}")
    @Operation(summary = "식물 별 일정 조회", description = "특정 식물의 일정을 조회합니다.")
    public ResponseEntity<CommonResponse<List<Calendar>>> getCalendarsByPlantId(@PathVariable Long plantId) {
        List<Calendar> calendars = calendarService.getCalendarsByPlantId(plantId);
        return ResponseEntity.ok(new CommonResponse<>("Calendars retrieved successfully", calendars));
    }

    @Authenticate(Role.MEMBER)
    @GetMapping("/date/{date}")
    @Operation(summary = "날짜 별 일정 조회", description = "특정 날짜의 일정을 조회합니다.")
    public ResponseEntity<CommonResponse<List<Calendar>>> getCalendarsByDate(@PathVariable LocalDate date) {
        List<Calendar> calendars = calendarService.getCalendarsByDate(date);
        return ResponseEntity.ok(new CommonResponse<>("Calendars retrieved successfully", calendars));
    }

    @Authenticate(Role.MEMBER)
    @GetMapping("/{id}")
    @Operation(summary = "일정 ID로 일정 조회", description = "ID를 통해 일정을 조회합니다.")
    public ResponseEntity<CommonResponse<Calendar>> getCalendarById(@PathVariable Long id) {
        Calendar calendar = calendarService.getCalendarById(id);
        return ResponseEntity.ok(new CommonResponse<>("Calendar retrieved successfully", calendar));
    }
}