package com.example.boardservice.adapter.in.web.response;

import com.example.boardservice.domain.Member;

public record MemberResponse(
        Long memberId,
        String nickname,
        String imageUrl,
        String thumbnailUrl
) {
    public static MemberResponse from(Member member) {
        return new MemberResponse(
                member.getMemberId(),
                member.getNickname(),
                member.getImageUrl(),
                member.getThumbnailUrl()
        );
    }
}
