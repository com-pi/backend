package com.example.authserver.domain;

import lombok.Builder;

@Builder
public record DeleteFollow(
        Long followerId,
        Long followeeId
) {
}
