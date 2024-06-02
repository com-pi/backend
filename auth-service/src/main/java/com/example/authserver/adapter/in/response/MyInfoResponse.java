package com.example.authserver.adapter.in.response;

import com.example.authserver.domain.Member;
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
    public static MyInfoResponse of(Member member){
        return new MyInfoResponse(
                member.getEmail(),
                member.getPhoneNumber(),
                member.getRole(),
                member.getNickname(),
                member.getIntroduction(),
                member.getImageUrl(),
                member.getThumbnailUrl(),
                member.getAddress(),
                member.getLocation()
        );
    }

}
