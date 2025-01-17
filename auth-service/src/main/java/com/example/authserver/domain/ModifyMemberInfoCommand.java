package com.example.authserver.domain;

import com.example.common.domain.Address;
import com.example.common.domain.Location;
import lombok.Builder;

@Builder
public record ModifyMemberInfoCommand(
        Long memberId,
        String nickName,
        String introduction,
        Boolean isPicUploaded,
        String profileImageUrl,
        String thumbnailUrl,
        Location location,
        Address address
) {
}
