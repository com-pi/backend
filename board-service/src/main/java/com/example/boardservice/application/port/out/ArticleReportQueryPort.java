package com.example.boardservice.application.port.out;

import com.example.boardservice.domain.ArticleReport;

import java.awt.print.Pageable;
import java.util.List;

public interface ArticleReportQueryPort {
    ArticleReport getReport(Long reportId);

    List<ArticleReport> getReportList(Pageable pageable);
}
