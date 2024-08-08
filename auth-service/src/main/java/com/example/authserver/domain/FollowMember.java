package com.example.authserver.domain;

import lombok.Builder;

@Builder
public record FollowMember(
        Long id,
        String nickname,
        String imageUrl,
        String thumbnailUrl,
        Boolean isFollowed
) {

}
