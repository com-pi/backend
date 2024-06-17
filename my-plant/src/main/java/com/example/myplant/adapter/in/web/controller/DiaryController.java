package com.example.myplant.adapter.in.web.controller;

import com.example.common.annotation.Authenticate;
import com.example.common.baseentity.CommonResponse;
import com.example.common.domain.Role;
import com.example.myplant.adapter.in.web.command.*;
import com.example.myplant.adapter.in.web.response.DiaryCreatedDateResponse;
import com.example.myplant.adapter.in.web.response.DiaryListResponse;
import com.example.myplant.adapter.in.web.response.DiaryResponse;
import com.example.myplant.application.port.in.DiaryUseCase;
import com.example.myplant.domain.Diary;
import com.example.myplant.security.PassportHolder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/diaries")
@RequiredArgsConstructor
@Tag(name = "일지 관리", description = "일지 작성 및 관리 API")
public class DiaryController {

    private final DiaryUseCase diaryUseCase;
    private final ObjectMapper objectMapper;

    @Operation(summary = "일지 등록", description = "새로운 일지를 등록합니다.")
    @Authenticate(Role.MEMBER)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CommonResponse<Long>> registerDiary(
            @Schema(
                    example = "{\"myPlantId\":1,\"title\":\"새로운 일지\",\"content\":\"새로운 일지입니다.\"," +
                            "\"createdDate\":\"2024-06-16\",\"plantCareList\":[\"WATERING\",\"SUNLIGHT\"]," +
                            "\"isPublished\":true,\"isPublic\":true}"
            )
            @RequestPart("data") String requestJson,
            @RequestPart("imageFiles") List<MultipartFile> imageFiles) throws JsonProcessingException {

        RegisterDiaryCommand command = objectMapper.readValue(requestJson, RegisterDiaryCommand.class);
        command.addMemberId(PassportHolder.getPassport().memberId());
        Long diaryId = diaryUseCase.registerDiary(command.toDomain(), imageFiles);
        return ResponseEntity.ok(new CommonResponse<>("일지가 등록되었습니다.", diaryId));
    }

    @Operation(summary = "일지 수정", description = "기존 일지를 수정합니다.")
    @Authenticate(Role.MEMBER)
    @PatchMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CommonResponse<Long>> updateDiary(
            @Schema(
                    example = "{\"diaryId\":1,\"title\":\"수정 일지\",\"content\":\"수정된 일지입니다.\"," +
                            "\"plantCareList\":[\"FERTILIZING\"],\"isPublished\":false,\"isPublic\":false}"
            )
            @RequestPart("data") String requestJson,
            @RequestPart(value = "imageFiles", required = false) List<MultipartFile> imageFiles) throws JsonProcessingException{
        UpdateDiaryCommand command = objectMapper.readValue(requestJson, UpdateDiaryCommand.class);
        command.addMemberId(PassportHolder.getPassport().memberId());

        Long diaryId = diaryUseCase.updateDiary(command.toDomain(), imageFiles);
        return ResponseEntity.ok(new CommonResponse<>("일지가 수정되었습니다.",diaryId));
    }

    @Operation(summary = "일지 삭제", description = "기존 일지를 삭제합니다.")
    @Authenticate(Role.MEMBER)
    @DeleteMapping("/{diaryId}")
    public ResponseEntity<CommonResponse<Long>> deleteDiary(@PathVariable Long diaryId) {
        DeleteDiaryCommand command = DeleteDiaryCommand.of(diaryId, PassportHolder.getPassport().memberId());
        Long deletedDiaryId = diaryUseCase.deleteDiary(command.toDomain());
        return ResponseEntity.ok(new CommonResponse<>("일지가 삭제되었습니다.", deletedDiaryId));
    }

    @Operation(summary = "일지 작성 상태 조회", description = "일지가 작성되어 있는 날짜와 id값을 조회합니다.")
    @Authenticate(Role.MEMBER)
    @GetMapping("/status")
    @Parameters({
            @Parameter(name = "startDate", description = "조회 조건 시작 날짜", example = "2024-06-01"),
            @Parameter(name = "endDate", description = "조회 조건 종료 날짜", example = "2024-06-30"),
    })
    public ResponseEntity<List<DiaryCreatedDateResponse>> getDiaryStatus(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        GetDiaryStatusCommand command = GetDiaryStatusCommand.of(startDate, endDate, PassportHolder.getPassport().memberId());
        List<Diary> diaryList = diaryUseCase.getDiaryStatus(command);
        List<DiaryCreatedDateResponse> responseList = diaryList.stream()
                .map(DiaryCreatedDateResponse::from)
                .toList();
        return ResponseEntity.ok(responseList);
    }

    @Operation(summary = "회원별 일지 목록 조회", description = "회원별로 일지 목록을 조회합니다.")
    @Authenticate(Role.MEMBER)
    @Parameters({
            @Parameter(name = "page", description = "페이지 번호", example = "0"),
            @Parameter(name = "size", description = "페이지 크기", example = "4"),
            @Parameter(name = "sort", description = "정렬 기준", example = "createdAt,DESC")
    })
    @GetMapping("/members/{memberId}")
    public ResponseEntity<DiaryListResponse> getDiaryByMemberId(
            @PageableDefault(size = 4, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @PathVariable Long memberId) {
        List<DiaryResponse> responseList = diaryUseCase.getDiaryByMemberId(memberId, pageable).stream()
                .map(DiaryResponse::from)
                .toList();
        return ResponseEntity.ok(DiaryListResponse.from(responseList));
    }

    @Operation(summary = "아이디별 공개 일지 조회", description = "공개 상태의 일지를 상세 조회합니다.")
    @Authenticate(Role.MEMBER)
    @GetMapping("/public/{diaryId}")
    public ResponseEntity<DiaryResponse> getDiaryByDiaryId(@PathVariable Long diaryId) {
        Diary diary = diaryUseCase.getDiaryById(diaryId);
        return ResponseEntity.ok(DiaryResponse.from(diary));
    }

    @Operation(summary = "아이디별 일지 조회", description = "일지를 상세 조회합니다.")
    @Authenticate(Role.MEMBER)
    @GetMapping("/personal/{diaryId}")
    public ResponseEntity<DiaryResponse> getPersonalDiaryByDiaryId(@PathVariable Long diaryId) {
        GetPersonalDiaryCommand command = GetPersonalDiaryCommand.of(diaryId, PassportHolder.getPassport().memberId());
        Diary diary = diaryUseCase.getPersonalDiaryById(command.toDomain());
        return ResponseEntity.ok(DiaryResponse.from(diary));
    }

}