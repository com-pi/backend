package com.example.authserver.domain;

import lombok.Builder;

@Builder
public record MemberBrief(
        Long id,
        String nickname,
        String imageUrl,
        String thumbnailUrl
) {
    public static MemberBrief fromDomain(Member member) {
        return MemberBrief.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .imageUrl(member.getImageUrl())
                .thumbnailUrl(member.getThumbnailUrl())
                .build();
    }
}
