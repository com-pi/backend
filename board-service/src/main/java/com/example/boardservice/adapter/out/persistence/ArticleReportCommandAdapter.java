package com.example.boardservice.adapter.out.persistence;

import com.example.boardservice.adapter.out.persistence.entity.ArticleReportEntity;
import com.example.boardservice.application.port.out.ArticleReportCommandPort;
import com.example.boardservice.domain.ArticleReport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArticleReportCommandAdapter implements ArticleReportCommandPort {

    private final ArticleReportRepository reportRepository;

    @Override
    public ArticleReport save(ArticleReport report) {
        ArticleReportEntity reportEntity = reportRepository.save(ArticleReportEntity.fromDomain(report));
        return reportEntity.toDomain();
    }
}
