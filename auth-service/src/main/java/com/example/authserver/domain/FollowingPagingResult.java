package com.example.authserver.domain;

import com.example.authserver.adapter.out.entity.FollowEntity;
import com.example.authserver.adapter.out.entity.MemberEntity;
import lombok.Builder;
import org.springframework.data.domain.Page;

import java.util.List;

@Builder
public record FollowingPagingResult(
        Long totalElement,
        Integer totalPage,
        List<FollowMember> followingMemberList
) {

    public static FollowingPagingResult of(Page<FollowEntity> followEntityPage) {
        return FollowingPagingResult.builder()
                .totalElement(followEntityPage.getTotalElements())
                .totalPage(followEntityPage.getTotalPages())
                .followingMemberList(followEntityPage.get().map(m -> MemberEntity.toFollowMember(m.getFollowee())).toList())
                .build();
    }

}
