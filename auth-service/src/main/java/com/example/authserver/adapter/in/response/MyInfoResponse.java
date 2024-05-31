package com.example.authserver.adapter.in.response;

import com.example.authserver.adapter.out.MemberEntity;
import com.example.common.domain.Address;
import com.example.common.domain.Location;
import com.example.common.domain.Role;

public record MyInfoResponse(
        String email,
        String phoneNumber,
        Role role,
        String nickname,
        String introduction,
        String imageUrl,
        String thumbnailUrl,
        Address address,
        Location location
) {
    public static MyInfoResponse of(MemberEntity memberEntity){
        return new MyInfoResponse(
                memberEntity.getEmail(),
                memberEntity.getPhoneNumber(),
                memberEntity.getRole(),
                memberEntity.getNickname(),
                memberEntity.getIntroduction(),
                memberEntity.getImageUrl(),
                memberEntity.getThumbnailUrl(),
                memberEntity.getAddress(),
                Location.of(memberEntity.getLocation())
        );
    }

}
