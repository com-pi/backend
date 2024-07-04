package com.example.boardservice.adapter.out.persistence.entity;

import com.example.boardservice.domain.ArticleReport;
import com.example.common.baseentity.BaseTimeAbstractEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "article_report")
public class ArticleReportEntity extends BaseTimeAbstractEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    private String content;

    private Long reporterId;

    private Long reportedId;

    private Long articleId;

    @Builder
    public ArticleReportEntity(Long reportId, String content, Long reporterId, Long reportedId, Long articleId) {
        this.reportId = reportId;
        this.content = content;
        this.reporterId = reporterId;
        this.reportedId = reportedId;
        this.articleId = articleId;
    }

    public static ArticleReportEntity fromDomain(ArticleReport report) {
        return ArticleReportEntity.builder()
                .reportId(report.getReportId())
                .content(report.getContent())
                .reporterId(report.getReporterId())
                .reportedId(report.getReportedId())
                .articleId(report.getArticleId())
                .build();
    }

    public ArticleReport toDomain() {
        return ArticleReport.builder()
                .reportId(this.reportId)
                .content(this.content)
                .reporterId(this.reporterId)
                .reportedId(this.reportedId)
                .articleId(this.articleId)
                .createdAt(this.getCreatedAt())
                .build();
    }
}