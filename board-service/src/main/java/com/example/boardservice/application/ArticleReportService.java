package com.example.boardservice.application;

import com.example.boardservice.application.port.in.ArticleReportUseCase;
import com.example.boardservice.application.port.out.ArticleReportCommandPort;
import com.example.boardservice.application.port.out.ArticleReportQueryPort;
import com.example.boardservice.domain.ArticleReport;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleReportService implements ArticleReportUseCase {

    private final ArticleReportCommandPort reportCommandPort;
    private final ArticleReportQueryPort reportQueryPort;

    @Override
    @Transactional
    public Long report(ArticleReport report) {
        ArticleReport savedReport = reportCommandPort.save(report);
        return savedReport.getReportId();
    }

    @Override
    public ArticleReport getReport(Long reportId) {
        return reportQueryPort.getReport(reportId);
    }

    @Override
    public List<ArticleReport> getReportList(Pageable pageable) {
        return reportQueryPort.getReportList(pageable);
    }
}
