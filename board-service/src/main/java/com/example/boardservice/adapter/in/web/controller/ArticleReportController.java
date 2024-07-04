package com.example.boardservice.adapter.in.web.controller;

import com.example.boardservice.adapter.in.web.command.ReportCommand;
import com.example.boardservice.adapter.in.web.request.ReportRequest;
import com.example.boardservice.adapter.in.web.response.ReportListResponse;
import com.example.boardservice.adapter.in.web.response.ReportResponse;
import com.example.boardservice.application.port.in.ArticleReportUseCase;
import com.example.boardservice.security.PassportHolder;
import com.example.common.annotation.Authenticate;
import com.example.common.baseentity.CommonResponse;
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
@RequestMapping("/reports")
@Tag(name = "게시글 신고", description = "게시글 신고 API")
public class ArticleReportController {

    private final ArticleReportUseCase reportUseCase;

    @Operation(summary = "게시글 신고", description = "게시글을 신고합니다.")
    @Authenticate(Role.MEMBER)
    @PostMapping
    public ResponseEntity<CommonResponse<Long>> report(@RequestBody ReportRequest request) {
        ReportCommand command = ReportCommand.of(request, PassportHolder.getPassport().memberId());
        Long reportId = reportUseCase.report(command.toDomain());
        return ResponseEntity.ok(new CommonResponse<>("게시글을 신고했습니다.", reportId));
    }

    @Operation(summary = "게시글 신고 상세 조회", description = "게시글을 신고 건을 상세 조회합니다.")
    @Authenticate(Role.ADMIN)
    @GetMapping("detail/{reportId}")
    public ResponseEntity<ReportResponse> getReport(@PathVariable final Long reportId) {
        ReportResponse response = ReportResponse.from(reportUseCase.getReport(reportId));
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "게시글 신고 목록 조회", description = "게시글을 신고 건을 목록 조회합니다.")
    @Authenticate(Role.ADMIN)
    @GetMapping("/list")
    @Parameters({
            @Parameter(name = "page", description = "페이지 번호", example = "0"),
            @Parameter(name = "size", description = "페이지 크기", example = "4"),
            @Parameter(name = "sort", description = "정렬 기준", example = "createdAt,DESC")
    })
    public ResponseEntity<ReportListResponse> getReportList(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        ReportListResponse response = ReportListResponse.from(reportUseCase.getReportList(pageable));
        return ResponseEntity.ok(response);
    }

}
