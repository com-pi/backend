package com.example.boardservice.adapter.in.web.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReportRequest {

    private final String content;
    private final Long reportedId;
    private final Long articleId;

}
