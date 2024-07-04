package com.example.boardservice.adapter.in.web.command;

import com.example.boardservice.adapter.in.web.request.ReportRequest;
import com.example.boardservice.domain.ArticleReport;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReportCommand {

    private Long reporterId;
    private final String content;
    private final Long reportedId;
    private final Long articleId;

    public static ReportCommand of(ReportRequest request, Long memberId) {
        return ReportCommand.builder()
                .reporterId(memberId)
                .content(request.getContent())
                .reportedId(request.getReportedId())
                .articleId(request.getArticleId())
                .build();
    }

    public ArticleReport toDomain() {
        return ArticleReport.builder()
                .reporterId(this.reporterId)
                .content(this.content)
                .reportedId(this.reportedId)
                .articleId(this.articleId)
                .build();
    }
}
