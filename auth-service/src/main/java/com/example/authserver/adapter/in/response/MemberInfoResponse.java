package com.example.authserver.adapter.in.response;

import com.example.authserver.adapter.out.MemberEntity;
import com.example.common.domain.Address;

public record MemberInfoResponse(
        String nickname,
        String introduction,
        String imageUrl,
        String thumbnailUrl,
        Address address
) {
    public static MemberInfoResponse from(MemberEntity memberEntity) {
        return new MemberInfoResponse(
                memberEntity.getNickname(),
                memberEntity.getIntroduction(),
                memberEntity.getImageUrl(),
                memberEntity.getThumbnailUrl(),
                memberEntity.getAddress()
        );
    }
}
