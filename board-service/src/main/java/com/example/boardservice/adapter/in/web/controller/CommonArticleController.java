package com.example.boardservice.adapter.in.web.controller;

import com.example.boardservice.adapter.in.web.command.GetArticleCommand;
import com.example.boardservice.adapter.in.web.command.GetArticleListCommand;
import com.example.boardservice.adapter.in.web.command.GetSearchedArticleListCommand;
import com.example.boardservice.adapter.in.web.response.CommonArticleListResponse;
import com.example.boardservice.adapter.in.web.response.CommonArticleResponse;
import com.example.boardservice.application.port.in.CommonArticleUseCase;
import com.example.boardservice.security.PassportHolder;
import com.example.common.annotation.Authenticate;
import com.example.common.domain.Role;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/common")
@Tag(name = "공통 게시글", description = "공통 게시글 API")
public class CommonArticleController {

    private final CommonArticleUseCase useCase;

    @Operation(summary = "게시글 목록 조회", description = "게시글을 목록으로 조회합니다.")
    @Authenticate(Role.MEMBER)
    @GetMapping("/list")
    @Parameters({
            @Parameter(name = "type", description = "1. 전체: common, 2. 자유: general, 3. 질문: qna, 4. 일지: diary", example = "common"),
            @Parameter(name = "page", description = "페이지 번호", example = "0"),
            @Parameter(name = "size", description = "페이지 크기", example = "4"),
            @Parameter(name = "sort", description = "정렬 기준", example = "createdAt,DESC")
    })
    public ResponseEntity<CommonArticleListResponse> getArticleList(
            @RequestParam("type") String type,
            @PageableDefault(size = 4, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        GetArticleListCommand command = GetArticleListCommand.of(PassportHolder.getPassport().memberId(), type, pageable);
        CommonArticleListResponse response = CommonArticleListResponse.from(useCase.getArticleList(command));
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "게시글 상세 조회", description = "게시글을 상세 조회합니다.")
    @Authenticate(Role.MEMBER)
    @GetMapping("/detail/{articleId}")
    public ResponseEntity<CommonArticleResponse> getArticle(@PathVariable Long articleId) {
        GetArticleCommand command = GetArticleCommand.of(PassportHolder.getPassport().memberId(), articleId);
        CommonArticleResponse response = CommonArticleResponse.from(useCase.getArticle(command.toDomain()));
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "해시태그 게시글 목록 조회", description = "해당 해시태그를 가진 게시글을 목록으로 조회합니다.")
    @Authenticate(Role.MEMBER)
    @GetMapping("/hashtags")
    @Parameters({
            @Parameter(name = "page", description = "페이지 번호", example = "0"),
            @Parameter(name = "size", description = "페이지 크기", example = "4"),
            @Parameter(name = "sort", description = "정렬 기준", example = "createdAt,DESC")
    })
    public ResponseEntity<CommonArticleListResponse> getArticleListByHashtag(
            @RequestParam final String name,
            @PageableDefault(size = 4, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        CommonArticleListResponse response = CommonArticleListResponse.from(useCase.getArticleListByHashtag(name, pageable));
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "게시글 검색", description = " 목록으로 조회합니다.")
    @Authenticate(Role.MEMBER)
    @GetMapping("/search")
    @Parameters({
            @Parameter(name = "page", description = "페이지 번호", example = "0"),
            @Parameter(name = "size", description = "페이지 크기", example = "4"),
            @Parameter(name = "sort", description = "정렬 기준", example = "createdAt,DESC"),
            @Parameter(name = "keyword", description = "검색 키워드", example = "게시글"),
            @Parameter(name = "type", description = "1. 전체: common, 2. 자유: general, 3. 질문: qna, 4. 일지: diary", example = "common")
    })
    public ResponseEntity<CommonArticleListResponse> searchArticleList(
            @PageableDefault(size = 4, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(required = false) String keyword,
            @RequestParam String type) {
        GetSearchedArticleListCommand command = GetSearchedArticleListCommand.of(
                PassportHolder.getPassport().memberId(), keyword, type, pageable);
        CommonArticleListResponse response = CommonArticleListResponse.from(useCase.searchArticleList(command));
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "사용자별 게시글 목록 조회", description = "게시글 목록을 사용자별로 조회합니다.")
    @Authenticate(Role.MEMBER)
    @GetMapping("/list/members")
    @Parameters({
            @Parameter(name = "page", description = "페이지 번호", example = "0"),
            @Parameter(name = "size", description = "페이지 크기", example = "4"),
            @Parameter(name = "sort", description = "정렬 기준", example = "createdAt,DESC")
    })
    public ResponseEntity<CommonArticleListResponse> getArticleListByMember(
            @PageableDefault(size = 4, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        CommonArticleListResponse response = CommonArticleListResponse.from(
                useCase.getArticleListByMember(PassportHolder.getPassport().memberId(), pageable));
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "사용자별 좋아요한 게시글 목록 조회", description = "사용자별 좋아요한 게시글 목록을 조회합니다.")
    @Authenticate(Role.MEMBER)
    @GetMapping("/list/members/likes")
    @Parameters({
            @Parameter(name = "page", description = "페이지 번호", example = "0"),
            @Parameter(name = "size", description = "페이지 크기", example = "4"),
            @Parameter(name = "sort", description = "정렬 기준", example = "createdAt,DESC")
    })
    public ResponseEntity<CommonArticleListResponse> getLikedArticleListByMember(
            @PageableDefault(size = 4, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        CommonArticleListResponse response = CommonArticleListResponse.from(
                useCase.getLikedArticleListByMember(PassportHolder.getPassport().memberId(), pageable));
        return ResponseEntity.ok(response);
    }
}
