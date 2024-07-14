package com.example.authserver.domain;

import com.example.authserver.adapter.out.entity.FollowEntity;
import com.example.authserver.adapter.out.entity.MemberEntity;
import lombok.Builder;
import org.springframework.data.domain.Page;

import java.util.List;

@Builder
public record FollowerPagingResult(
        Long totalElement,
        Integer totalPage,
        List<FollowMember> followerMemberList
) {

    public static FollowerPagingResult of(Page<FollowEntity> followEntityPage) {
        return FollowerPagingResult.builder()
                .totalElement(followEntityPage.getTotalElements())
                .totalPage(followEntityPage.getTotalPages())
                .followerMemberList(followEntityPage.get().map(m -> MemberEntity.toFollowMember(m.getFollower())).toList())
                .build();
    }

}
