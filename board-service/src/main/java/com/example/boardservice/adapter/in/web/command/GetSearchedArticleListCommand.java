package com.example.boardservice.adapter.in.web.command;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

@Getter
@Builder
public class GetSearchedArticleListCommand {
    private Long memberId;
    private String keyword;
    private String type;
    private Pageable pageable;

    public static GetSearchedArticleListCommand of(Long memberId, String keyword, String type, Pageable pageable) {
        return GetSearchedArticleListCommand.builder()
                .memberId(memberId)
                .keyword(keyword)
                .type(type)
                .pageable(pageable)
                .build();
    }
}
