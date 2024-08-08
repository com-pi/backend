package com.example.authserver.domain;

import lombok.Builder;

import java.util.List;

@Builder
public record FollowerPagingResult(
        Long totalElement,
        Integer totalPage,
        List<FollowMember> followerMemberList
) {

}
