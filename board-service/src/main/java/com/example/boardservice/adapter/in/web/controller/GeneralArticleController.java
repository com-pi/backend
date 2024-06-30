package com.example.boardservice.adapter.in.web.controller;

import com.example.boardservice.adapter.in.web.command.DeleteGeneralArticleCommand;
import com.example.boardservice.adapter.in.web.request.PostGeneralRequest;
import com.example.boardservice.adapter.in.web.request.UpdateGeneralRequest;
import com.example.boardservice.adapter.in.web.response.GeneralArticleListResponse;
import com.example.boardservice.adapter.in.web.response.GeneralArticleResponse;
import com.example.boardservice.application.port.in.GeneralArticleUseCase;
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
@RequestMapping("/general")
@Tag(name = "일반 게시글", description = "일반 게시글 API")
public class GeneralArticleController {

    private final GeneralArticleUseCase generalArticleUseCase;
    private final ObjectMapper objectMapper;

    @Operation(summary = "일반 게시글 등록", description = "일반 게시글을 등록합니다.")
    @Authenticate(Role.MEMBER)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CommonResponse<Long>> post(
            @Schema(
                    example = "{"
                            + "\"title\":\"일반 게시글입니다.\","
                            + "\"content\":\"일반 게시글을 작성합니다.\","
                            + "\"hashtagList\":[\"해시태그1\", \"해시태그2\", \"해시태그3\"]"
                            + "}"
            )
            @RequestPart("data") String requestJson,
            @RequestPart(value = "imageFiles", required = false) List<MultipartFile> imageFiles) throws JsonProcessingException {
        PostGeneralRequest request = objectMapper.readValue(requestJson, PostGeneralRequest.class);
        Long articleId = generalArticleUseCase.post(request.toDomain(PassportHolder.getPassport().memberId()), imageFiles);
        return ResponseEntity.ok(new CommonResponse<>("일반 게시글이 등록되었습니다.", articleId));
    }

    @Operation(summary = "일반 게시글 수정", description = "일반 게시글을 수정합니다.")
    @Authenticate(Role.MEMBER)
    @PatchMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CommonResponse<Long>> update(
            @Schema(
                    example = "{"
                            + "\"articleId\": 1,"
                            + "\"title\":\"수정 게시글입니다.\","
                            + "\"content\":\"일반 게시글을 수정합니다.\","
                            + "\"hashtagList\":[\"수정 해시태그1\", \"수정 해시태그2\", \"수정 해시태그3\"]"
                            + "}"
            )
            @RequestPart("data") String requestJson,
            @RequestPart(value = "imageFiles", required = false) List<MultipartFile> imageFiles) throws JsonProcessingException {
        UpdateGeneralRequest request = objectMapper.readValue(requestJson, UpdateGeneralRequest.class);
        Long articleId = generalArticleUseCase.update(request.toDomain(PassportHolder.getPassport().memberId()), imageFiles);
        return ResponseEntity.ok(new CommonResponse<>("일반 게시글이 수정되었습니다.", articleId));
    }

    @Operation(summary = "일반 게시글 삭제", description = "일반 게시글을 삭제합니다.")
    @Authenticate(Role.MEMBER)
    @DeleteMapping("/{articleId}")
    public ResponseEntity<CommonResponse<Long>> delete(@PathVariable Long articleId) {
        DeleteGeneralArticleCommand command = DeleteGeneralArticleCommand.of(PassportHolder.getPassport().memberId(), articleId);
        Long deletedArticleId = generalArticleUseCase.delete(command.toDomain());
        return ResponseEntity.ok(new CommonResponse<>("일반 게시글이 삭제되었습니다.", deletedArticleId));
    }

    @Operation(summary = "일반 게시글 목록 조회", description = "일반 게시글을 목록으로 조회합니다.")
    @Authenticate(Role.MEMBER)
    @GetMapping("/list")
    @Parameters({
            @Parameter(name = "page", description = "페이지 번호", example = "0"),
            @Parameter(name = "size", description = "페이지 크기", example = "4"),
            @Parameter(name = "sort", description = "정렬 기준", example = "createdAt,DESC")
    })
    public ResponseEntity<GeneralArticleListResponse> getGeneralArticleList(
            @PageableDefault(size = 4, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        GeneralArticleListResponse response = GeneralArticleListResponse.from(generalArticleUseCase.getGeneralArticleList(pageable));
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "일반 게시글 상세 조회", description = "일반 게시글을 상세 조회합니다.")
    @Authenticate(Role.MEMBER)
    @GetMapping("/{articleId}")
    public ResponseEntity<GeneralArticleResponse> getGeneralArticle(@PathVariable Long articleId) {
        GeneralArticleResponse response = GeneralArticleResponse.from(generalArticleUseCase.getGeneralArticle(articleId));
        return ResponseEntity.ok(response);
    }

}
