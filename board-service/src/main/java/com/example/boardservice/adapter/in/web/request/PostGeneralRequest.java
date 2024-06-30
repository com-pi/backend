package com.example.boardservice.adapter.in.web.request;

import com.example.boardservice.domain.GeneralArticleCommand;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PostGeneralRequest {

    private Long memberId;
    private final String title;
    private final String content;
    private final List<String> hashtagList;

    public GeneralArticleCommand toDomain(Long memberId) {
        return GeneralArticleCommand.builder()
                .memberId(memberId)
                .title(title)
                .content(content)
                .hashtagList(hashtagList)
                .build();
    }
}
