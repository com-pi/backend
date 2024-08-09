package com.example.authserver.domain;

import lombok.Builder;

@Builder
public record MemberBrief(
        Long id,
        String nickname,
        String imageUrl,
        String thumbnailUrl
) {

}
