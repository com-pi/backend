package com.example.boardservice.adapter.in.web.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostReplyRequest {

    private final String content;
    private final Long parentId;
    private final Long articleId;

}
