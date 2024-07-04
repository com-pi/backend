package com.example.boardservice.adapter.in.web.controller;

import com.example.boardservice.adapter.in.web.response.CommonArticleListResponse;
import com.example.boardservice.application.port.in.CommonArticleUseCase;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/common")
@Tag(name = "공통 게시글", description = "공통 게시글 API")
public class CommonArticleController {

    private final CommonArticleUseCase useCase;

    @Operation(summary = "전체 게시글 목록 조회", description = "전체 게시글을 목록으로 조회합니다.")
    @Authenticate(Role.MEMBER)
    @GetMapping("/list")
    @Parameters({
            @Parameter(name = "page", description = "페이지 번호", example = "0"),
            @Parameter(name = "size", description = "페이지 크기", example = "4"),
            @Parameter(name = "sort", description = "정렬 기준", example = "createdAt,DESC")
    })
    public ResponseEntity<CommonArticleListResponse> getGeneralArticleList(
            @PageableDefault(size = 4, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        CommonArticleListResponse response = CommonArticleListResponse.from(useCase.getCommonArticleList(pageable));
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

}
