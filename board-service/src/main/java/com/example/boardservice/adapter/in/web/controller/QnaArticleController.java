package com.example.boardservice.adapter.in.web.controller;

import com.example.boardservice.adapter.in.web.command.DeleteQnaArticleCommand;
import com.example.boardservice.adapter.in.web.request.PostQnARequest;
import com.example.boardservice.adapter.in.web.request.UpdateQnaRequest;
import com.example.boardservice.adapter.in.web.response.QnaArticleListResponse;
import com.example.boardservice.adapter.in.web.response.QnaArticleResponse;
import com.example.boardservice.application.port.in.QnaArticleUseCase;
import com.example.boardservice.security.PassportHolder;
import com.example.common.annotation.Authenticate;
import com.example.common.baseentity.CommonResponse;
import com.example.common.domain.Role;
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

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/qna")
@Tag(name = "질문 게시글", description = "질문 게시글 API")
public class QnaArticleController {

    private final QnaArticleUseCase qnaArticleUseCase;
    private final ObjectMapper objectMapper;

    @Operation(summary = "질문 게시글 등록", description = "질문 게시글을 등록합니다.")
    @Authenticate(Role.MEMBER)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CommonResponse<Long>> post(
            @Schema(
                    example = "{"
                            + "\"title\":\"질문 게시글입니다.\","
                            + "\"content\":\"질문 게시글을 작성합니다.\","
                            + "\"hashtagList\":[\"해시태그1\", \"해시태그2\", \"해시태그3\"]"
                            + "}"
            )
            @RequestPart("data") String requestJson,
            @RequestPart(value = "imageFiles", required = false) List<MultipartFile> imageFiles) throws JsonProcessingException {
        PostQnARequest request = objectMapper.readValue(requestJson, PostQnARequest.class);
        Long articleId = qnaArticleUseCase.post(request.toDomain(PassportHolder.getPassport().memberId()), imageFiles);
        return ResponseEntity.ok(new CommonResponse<>("질문 게시글이 등록되었습니다.", articleId));
    }

    @Operation(summary = "질문 게시글 수정", description = "질문 게시글을 수정합니다.")
    @Authenticate(Role.MEMBER)
    @PatchMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CommonResponse<Long>> update(
            @Schema(
                    example = "{"
                            + "\"articleId\": 1,"
                            + "\"title\":\"수정 게시글입니다.\","
                            + "\"content\":\"질문 게시글을 수정합니다.\","
                            + "\"hashtagList\":[\"수정 해시태그1\", \"수정 해시태그2\", \"수정 해시태그3\"]"
                            + "}"
            )
            @RequestPart("data") String requestJson,
            @RequestPart(value = "imageFiles", required = false) List<MultipartFile> imageFiles) throws JsonProcessingException {
        UpdateQnaRequest request = objectMapper.readValue(requestJson, UpdateQnaRequest.class);
        Long articleId = qnaArticleUseCase.update(request.toDomain(PassportHolder.getPassport().memberId()), imageFiles);
        return ResponseEntity.ok(new CommonResponse<>("질문 게시글이 수정되었습니다.", articleId));
    }

    @Operation(summary = "질문 게시글 삭제", description = "질문 게시글을 삭제합니다.")
    @Authenticate(Role.MEMBER)
    @DeleteMapping("/{articleId}")
    public ResponseEntity<CommonResponse<Long>> delete(@PathVariable Long articleId) {
        DeleteQnaArticleCommand command = DeleteQnaArticleCommand.of(PassportHolder.getPassport().memberId(), articleId);
        Long deletedArticleId = qnaArticleUseCase.delete(command.toDomain());
        return ResponseEntity.ok(new CommonResponse<>("질문 게시글이 삭제되었습니다.", deletedArticleId));
    }

    @Operation(summary = "질문 게시글 목록 조회", description = "질문 게시글을 목록으로 조회합니다.")
    @Authenticate(Role.MEMBER)
    @GetMapping("/list")
    @Parameters({
            @Parameter(name = "page", description = "페이지 번호", example = "0"),
            @Parameter(name = "size", description = "페이지 크기", example = "4"),
            @Parameter(name = "sort", description = "정렬 기준", example = "createdAt,DESC")
    })
    public ResponseEntity<QnaArticleListResponse> getQnAArticleList(
            @PageableDefault(size = 4, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        QnaArticleListResponse response = QnaArticleListResponse.from(qnaArticleUseCase.getQnaArticleList(pageable));
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "질문 게시글 상세 조회", description = "질문 게시글을 상세 조회합니다.")
    @Authenticate(Role.MEMBER)
    @GetMapping("/{articleId}")
    public ResponseEntity<QnaArticleResponse> getQnAArticle(@PathVariable Long articleId) {
        QnaArticleResponse response = QnaArticleResponse.from(qnaArticleUseCase.getQnaArticle(articleId));
        return ResponseEntity.ok(response);
    }

}
