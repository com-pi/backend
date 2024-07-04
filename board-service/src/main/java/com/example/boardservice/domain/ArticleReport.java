package com.example.boardservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleReport {

    private Long reportId;

    private String content;

    private Long reporterId;

    private Long reportedId;

    private Long articleId;

    private LocalDateTime createdAt;

}
