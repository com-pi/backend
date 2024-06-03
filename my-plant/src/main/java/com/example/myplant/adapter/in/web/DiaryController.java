package com.example.myplant.adapter.in.web;

import com.example.common.annotation.Authenticate;
import com.example.common.domain.Role;
import com.example.myplant.domain.Diary;
import com.example.myplant.application.service.DiaryService;
import com.example.common.baseentity.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/diaries")
@Tag(name = "일지 관리", description = "일지 추가 및 수정 API")
public class DiaryController {
    private final DiaryService diaryService;

    @Autowired
    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }

    @Authenticate(Role.MEMBER)
    @PostMapping("/add")
    @Operation(summary = "일지 추가", description = "새로운 일지를 추가합니다.")
    public ResponseEntity<CommonResponse<Diary>> addDiary(@RequestBody Diary diary) {
        Diary addedDiary = diaryService.createDiary(diary);
        return ResponseEntity.ok(new CommonResponse<>("Diary added successfully",addedDiary));
    }

    @Authenticate(Role.MEMBER)
    @PutMapping("/update/{id}")
    @Operation(summary = "일지 수정", description = "기존 일지를 수정합니다.")
    public ResponseEntity<CommonResponse<Diary>> updateDiary(@PathVariable Long id, @RequestBody Diary diaryDetails) {
        Diary updatedDiary = diaryService.updateDiary(id, diaryDetails);
        return ResponseEntity.ok(new CommonResponse<>("Diary updated successfully",updatedDiary));
    }

    @Authenticate(Role.MEMBER)
    @GetMapping("/list")
    @Operation(summary = "일지 목록 조회", description = "모든 일지를 조회합니다.")
    public ResponseEntity<CommonResponse<List<Diary>>> getAllDiaries() {
        List<Diary> diaries = diaryService.getAllDiaries();
        return ResponseEntity.ok(new CommonResponse<>("Diaries retrieved successfully",diaries));
    }
}