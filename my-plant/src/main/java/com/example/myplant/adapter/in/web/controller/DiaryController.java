//package com.example.myplant.adapter.in.web.controller;
//
//import com.example.common.annotation.Authenticate;
//import com.example.common.baseentity.CommonResponse;
//import com.example.common.domain.Role;
//import com.example.myplant.adapter.in.web.command.DiaryCommand;
//import com.example.myplant.adapter.in.web.command.UpdateDiaryCommand;
//import com.example.myplant.application.port.in.DiaryUseCase;
//import com.example.myplant.domain.Diary;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/diaries")
//@Tag(name = "일지 관리", description = "일지 작성 및 관리 API")
//public class DiaryController {
//    private final DiaryUseCase diaryUseCase;
//    private final ObjectMapper objectMapper;
//    @Autowired
//    public DiaryController(DiaryUseCase diaryUseCase, ObjectMapper objectMapper) {
//        this.diaryUseCase = diaryUseCase;
//        this.objectMapper = objectMapper;
//    }
//
//    @Authenticate(Role.MEMBER)
//    @PostMapping(value = "/create",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    @Operation(summary = "일지 추가", description = "새로운 일지를 추가합니다.")
//    public ResponseEntity<CommonResponse<Long>> createDiary(
//            @RequestPart("diaryData") String diaryData, @RequestPart("images") List<MultipartFile> images) throws JsonProcessingException {
//
//        DiaryCommand command = objectMapper.readValue(diaryData, DiaryCommand.class);
//        command.setImages(images);
//        Long diaryId = diaryUseCase.createDiary(command);
//        return ResponseEntity.ok(new CommonResponse<>("Diary added successfully", diaryId));
//    }
//
//    @Authenticate(Role.MEMBER)
//    @PutMapping(value ="/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    @Operation(summary = "일지 수정", description = "기존 일지를 수정합니다.")
//    public ResponseEntity<CommonResponse<Diary>> updateDiary(
//            @RequestPart("diaryData") String diaryData, @RequestPart("images") List<MultipartFile> images) throws JsonProcessingException{
//        UpdateDiaryCommand command = objectMapper.readValue(diaryData, UpdateDiaryCommand.class);
//        command.setImages(images);
//
//        diaryUseCase.updateDiary(command);
//        return ResponseEntity.ok(new CommonResponse<>("Diary updated successfully",null));
//    }
//
//    @Authenticate(Role.MEMBER)
//    @DeleteMapping("/delete/{id}")
//    @Operation(summary = "일지 삭제", description = "기존 일지를 삭제합니다.")
//    public ResponseEntity<CommonResponse<Void>> deleteDiary(@PathVariable Long id) {
//        diaryUseCase.deleteDiary(id);
//        return ResponseEntity.ok(new CommonResponse<>("Diary deleted successfully", null));
//    }
//
//    @Authenticate(Role.MEMBER)
//    @GetMapping("/list")
//    @Operation(summary = "일지 목록 조회", description = "모든 일지를 조회합니다.")
//    public ResponseEntity<CommonResponse<List<Diary>>> getAllDiaries() {
//        List<Diary> diaries = diaryUseCase.getAllDiaries();
//        return ResponseEntity.ok(new CommonResponse<>("Diaries retrieved successfully",diaries));
//    }
//
//
//}