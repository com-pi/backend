package com.example.boardservice.application.port.in;

import com.example.boardservice.domain.ArticleReport;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArticleReportUseCase {
    Long report(ArticleReport report);

    ArticleReport getReport(Long reportId);

    List<ArticleReport> getReportList(Pageable pageable);
}
