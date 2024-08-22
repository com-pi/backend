package com.example.authserver.adapter.in.response;

import com.example.authserver.domain.Member;
import com.example.common.domain.Address;
import com.example.common.domain.Location;
import com.example.common.domain.Role;

public record MyInfoResponse(
        Long id,
        String email,
        String phoneNumber,
        Role role,
        String nickname,
        String introduction,
        String imageUrl,
        String thumbnailUrl,
        Address address,
        Location location,
        Integer followerCount,
        Integer followingCount
) {
    public static MyInfoResponse of(Member member, Integer followerCount, Integer followingCount) {
        return new MyInfoResponse(
                member.getId(),
                member.getEmail(),
                member.getPhoneNumber(),
                member.getRole(),
                member.getNickname(),
                member.getIntroduction(),
                member.getImageUrl(),
                member.getThumbnailUrl(),
                member.getAddress(),
                member.getLocation(),
                followerCount,
                followingCount
        );
    }

}
