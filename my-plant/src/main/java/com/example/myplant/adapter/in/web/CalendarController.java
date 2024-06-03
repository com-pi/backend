package com.example.myplant.adapter.in.web;

import com.example.common.annotation.Authenticate;
import com.example.common.domain.Role;
import com.example.myplant.domain.Calendar;
import com.example.myplant.application.service.CalendarService;
import com.example.common.baseentity.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/calendars")
@Tag(name = "캘린더 관리", description = "캘린더 추가 및 수정 API")
public class CalendarController {
    private final CalendarService calendarService;

    @Autowired
    public CalendarController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @Authenticate(Role.MEMBER)
    @PostMapping("/add")
    @Operation(summary = "캘린더 추가", description = "새로운 캘린더를 추가합니다.")
    public ResponseEntity<CommonResponse<Calendar>> addCalendar(@RequestBody Calendar calendar) {
        Calendar addedCalendar = calendarService.createCalendar(calendar);
        return ResponseEntity.ok(new CommonResponse<>("Calendar added successfully",addedCalendar));
    }

    @Authenticate(Role.MEMBER)
    @PutMapping("/update/{id}")
    @Operation(summary = "캘린더 수정", description = "기존 캘린더를 수정합니다.")
    public ResponseEntity<CommonResponse<Calendar>> updateCalendar(@PathVariable Long id, @RequestBody Calendar calendarDetails) {
        Calendar updatedCalendar = calendarService.updateCalendar(id, calendarDetails);
        return ResponseEntity.ok(new CommonResponse<>("Calendar updated successfully", updatedCalendar));
    }

    @Authenticate(Role.MEMBER)
    @GetMapping("/list")
    @Operation(summary = "캘린더 목록 조회", description = "모든 캘린더를 조회합니다.")
    public ResponseEntity<CommonResponse<List<Calendar>>> getAllCalendars() {
        List<Calendar> calendars = calendarService.getAllCalendars();
        return ResponseEntity.ok(new CommonResponse<>("Calendars retrieved successfully",calendars));
    }
}