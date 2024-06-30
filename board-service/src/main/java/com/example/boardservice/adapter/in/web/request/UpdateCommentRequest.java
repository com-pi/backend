package com.example.boardservice.adapter.in.web.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateCommentRequest {
    private final String content;
}
