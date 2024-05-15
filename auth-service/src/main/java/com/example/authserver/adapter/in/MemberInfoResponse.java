package com.example.authserver.adapter.in;

import com.example.authserver.domain.Member;
import com.example.common.domain.Address;

public record MemberInfoResponse(
        String nickname,
        String introduction,
        String imageUrl,
        String thumbnailUrl,
        Address address
) {
    public static MemberInfoResponse from(Member member) {
        return new MemberInfoResponse(
                member.getNickname(),
                member.getIntroduction(),
                member.getImageUrl(),
                member.getThumbnailUrl(),
                member.getAddress()
        );
    }
}
