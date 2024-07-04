package com.example.boardservice.adapter.in.web.response;

import com.example.boardservice.domain.ArticleReport;

import java.time.LocalDateTime;

public record ReportResponse(
        Long reportId,
        String content,
        Long reporterId,
        Long reportedId,
        Long articleId,
        LocalDateTime createdAt
) {
    public static ReportResponse from(ArticleReport report) {
        return new ReportResponse(
                report.getReportId(),
                report.getContent(),
                report.getReporterId(),
                report.getReportedId(),
                report.getArticleId(),
                report.getCreatedAt()
        );
    }
}
