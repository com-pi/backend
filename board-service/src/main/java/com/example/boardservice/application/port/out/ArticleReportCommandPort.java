package com.example.boardservice.application.port.out;

import com.example.boardservice.domain.ArticleReport;

public interface ArticleReportCommandPort {
    ArticleReport save(ArticleReport report);
}
