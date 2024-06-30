package com.example.boardservice.adapter.in.web.response;

import com.example.boardservice.domain.Member;

public record MemberResponse(
        Long memberId,
        String imageUrl,
        String nickname
) {
    public static MemberResponse from(Member member) {
        return new MemberResponse(
                member.getMemberId(),
                member.getImageUrl(),
                member.getNickname()
        );
    }
}
