package com.example.boardservice.adapter.out.persistence;

import com.example.boardservice.adapter.out.persistence.entity.ArticleReportEntity;
import com.example.boardservice.application.port.out.ArticleReportQueryPort;
import com.example.boardservice.domain.ArticleReport;
import com.example.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.awt.print.Pageable;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ArticleReportQueryAdapter implements ArticleReportQueryPort {

    private final ArticleReportRepository reportRepository;

    @Override
    public ArticleReport getReport(Long reportId) {
        ArticleReportEntity reportEntity = reportRepository.findById(reportId)
                .orElseThrow(() -> new NotFoundException(ArticleReportEntity.class));
        return reportEntity.toDomain();
    }

    @Override
    public List<ArticleReport> getReportList(Pageable pageable) {
        Page<ArticleReportEntity> reportEntityPage = reportRepository.findAll(pageable);
        return null;
    }
}
