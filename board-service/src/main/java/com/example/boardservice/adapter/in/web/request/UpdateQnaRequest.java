package com.example.boardservice.adapter.in.web.request;

import com.example.boardservice.domain.QnaArticleCommand;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class UpdateQnaRequest {

    private Long memberId;
    private Long articleId;
    private final String title;
    private final String content;
    private final List<String> hashtagList;

    public QnaArticleCommand toDomain(Long memberId) {
        return QnaArticleCommand.builder()
                .articleId(articleId)
                .memberId(memberId)
                .title(title)
                .content(content)
                .hashtagList(hashtagList)
                .build();
    }
}
