package com.example.boardservice.adapter.in.web.response;


import com.example.boardservice.domain.ArticleReport;

import java.util.List;

public record ReportListResponse(
        List<ReportResponse> responseList
) {
    public static ReportListResponse from(List<ArticleReport> reportList) {
        List<ReportResponse> responseList = reportList.stream()
                .map(ReportResponse::from)
                .toList();
        return new ReportListResponse(responseList);
    }
}
