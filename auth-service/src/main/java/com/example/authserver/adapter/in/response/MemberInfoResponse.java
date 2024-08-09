package com.example.authserver.adapter.in.response;

import com.example.authserver.domain.Member;
import com.example.common.domain.Address;

public record MemberInfoResponse(
        String nickname,
        String introduction,
        String imageUrl,
        String thumbnailUrl,
        Address address,
        Integer followerCount,
        Integer followingCount,
        Boolean isFollowed
) {
    public static MemberInfoResponse from(Member member, Integer followerCount, Integer followingCount, Boolean isFollowed) {
        return new MemberInfoResponse(
                member.getNickname(),
                member.getIntroduction(),
                member.getImageUrl(),
                member.getThumbnailUrl(),
                member.getAddress(),
                followerCount,
                followingCount,
                isFollowed
        );
    }
}
