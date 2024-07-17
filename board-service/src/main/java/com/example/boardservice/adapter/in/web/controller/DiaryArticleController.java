package com.example.boardservice.adapter.in.web.controller;

import com.example.boardservice.adapter.in.web.request.PostDiaryRequest;
import com.example.boardservice.adapter.out.persistence.entity.DiaryArticleUseCase;
import com.example.common.baseentity.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diaries")
@Tag(name = "일지 게시글", description = "일지 게시글 API")
public class DiaryArticleController {

    private final DiaryArticleUseCase diaryArticleUseCase;

    // @TODO @Authenticate
    @Operation(summary = "일지 게시글 등록", description = "일지 게시글을 연동하여 등록합니다.")
    @PostMapping
    public ResponseEntity<CommonResponse<Long>> post(@RequestBody PostDiaryRequest request) {
        Long articleId = diaryArticleUseCase.post(request.toDomain());
        return ResponseEntity.ok(new CommonResponse<>("일지 게시글이 등록되었습니다.", articleId));
    }



}
