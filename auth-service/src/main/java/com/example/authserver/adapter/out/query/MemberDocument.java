package com.example.authserver.adapter.out.query;

import com.example.authserver.domain.Member;
import com.example.authserver.util.GeomUtil;
import com.example.common.domain.Address;
import com.example.common.domain.Role;
import lombok.*;
import org.locationtech.jts.geom.Point;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Document(collection = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class MemberDocument {

    @Id
    private String id;

    @Field("kakao_id")
    private String kakaoId;

    @Field("naver_id")
    private String naverId;

    @Field("email")
    private String email;

    @Field("phone_number")
    private String phoneNumber;

    private String password;

    private Role role;

    @Field("nickname")
    private String nickname;

    private String introduction;

    @Field("image_url")
    private String imageUrl;

    @Field("thumbnail_url")
    private String thumbnailUrl;

    private Point location;

    private Address address;

    @Field("last_login")
    private LocalDateTime lastLogin;

    public static Member toDomain(MemberDocument memberDocument) {
        return Member.builder()
                .id(memberDocument.getId() != null ? Long.parseLong(memberDocument.getId()) : null)
                .kakaoId(memberDocument.getKakaoId())
                .naverId(memberDocument.getNaverId())
                .email(memberDocument.getEmail())
                .phoneNumber(memberDocument.getPhoneNumber())
                .password(memberDocument.getPassword())
                .role(memberDocument.getRole())
                .nickname(memberDocument.getNickname())
                .introduction(memberDocument.getIntroduction())
                .imageUrl(memberDocument.getImageUrl())
                .thumbnailUrl(memberDocument.getThumbnailUrl())
                .location(GeomUtil.createLocation(memberDocument.getLocation()))
                .address(memberDocument.getAddress())
                .lastLogin(memberDocument.getLastLogin())
                .build();
    }

}

