package com.example.myplant.adapter.in.web.controller;

import com.example.common.baseentity.CommonResponse;
import com.example.myplant.adapter.in.web.request.CreateScheduleRequest;
import com.example.myplant.adapter.in.web.request.UpdateScheduleRequest;
import com.example.myplant.adapter.in.web.response.ScheduleMainResponseList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;


@Tag(name = "일정 관리", description = "일정 추가 및 수정 API")
public interface ScheduleSwaggerUI {

    @Operation(summary = "일정 추가", description = "새로운 일정을 추가합니다.")
    ResponseEntity<CommonResponse<Long>> createSchedule(
        @Schema(
            example = "{\"title\":\"테스트\","
                + " \"startDateTime\":\"2024-06-15T16:14:00\","
                + " \"endDateTime\":\"2024-06-16T16:14:00\","
                + " \"frequency\":2,"
                + " \"colorType\":\"YELLOW\"}"
        ) CreateScheduleRequest request);

    @Operation(summary = "일정 수정", description = "기존 일정을 수정합니다.")
    ResponseEntity<CommonResponse<Long>> updateSchedule(@PathVariable Long scheduleId,
        @Schema(
            example = "{ \"title\":\"테스트\","
                + "\"endDateTime\":\"2024-06-16T20:00:00\"}"
        ) UpdateScheduleRequest command);

    @Operation(summary = "일정 상태 수정", description = "일정의 상태를 수정합니다.")
    ResponseEntity<CommonResponse<Long>> updateScheduleStatus(@PathVariable Long scheduleId);

    @Operation(summary = "메인페이지에서 일정 조회", description = "메인 페이지에서 일정을 조회합니다.")
    ResponseEntity<ScheduleMainResponseList> getMainPageScheduleList();

    @Operation(summary = "오늘의 일정 조회", description = "오늘의 일정을 조회합니다.")
    ResponseEntity<ScheduleMainResponseList> getTodayScheduleList();

    @Operation(summary = "다가오는 일정 조회", description = "다가오는 일정을 조회합니다. (7일 기준)")
    ResponseEntity<ScheduleMainResponseList> getUpcomingScheduleList();

    @Operation(summary = "(달력 폼)일정 상세 페이지 목록 조회", description = "상세 일정을 목록으로 조회합니다.")
    ResponseEntity<ScheduleMainResponseList> getScheduleList();

    @Operation(summary = "(달력 폼)일정 상세 페이지 일자별 상세 조회", description = "상세 일정을 일자별로 단건 조회합니다.")
    ResponseEntity<ScheduleMainResponseList> getScheduleByDate();


}