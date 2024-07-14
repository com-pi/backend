package com.example.authserver.domain;

import lombok.Builder;

@Builder
public record Follow(
        Member follower,
        Member followee
) {

}
