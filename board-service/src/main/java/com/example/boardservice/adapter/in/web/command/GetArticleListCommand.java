package com.example.boardservice.adapter.in.web.command;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

@Builder
@Getter
public class GetArticleListCommand {

    private Long memberId;
    private String type;
    private Pageable pageable;

    public static GetArticleListCommand of(Long memberId, String type, Pageable pageable) {
        return GetArticleListCommand.builder()
                .memberId(memberId)
                .type(type)
                .pageable(pageable)
                .build();
    }

}
