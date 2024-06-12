package com.example.authserver.adapter.out.query;

import com.example.authserver.domain.Member;
import com.example.common.domain.Address;
import com.example.common.domain.Location;
import com.example.common.domain.Role;
import lombok.*;
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

    private Location location;

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
                .location(memberDocument.getLocation())
                .address(memberDocument.getAddress())
                .lastLogin(memberDocument.getLastLogin())
                .build();
    }

    public static MemberDocument fromDomain(Member member) {
        return MemberDocument.builder()
                .id(member.getId().toString())
                .kakaoId(member.getKakaoId())
                .naverId(member.getNaverId())
                .email(member.getEmail())
                .phoneNumber(member.getPhoneNumber())
                .password(member.getPassword())
                .role(member.getRole())
                .nickname(member.getNickname())
                .introduction(member.getIntroduction())
                .imageUrl(member.getImageUrl())
                .thumbnailUrl(member.getThumbnailUrl())
                .location(member.getLocation())
                .address(member.getAddress())
                .lastLogin(member.getLastLogin())
                .build();
    }

    public void update(Member member){
        password = member.getPassword();
        role = member.getRole();
        nickname = member.getNickname();
        introduction = member.getIntroduction();
        imageUrl = member.getImageUrl();
        thumbnailUrl = member.getThumbnailUrl();
        location = member.getLocation();
        address = member.getAddress();
        lastLogin = member.getLastLogin();
    }

}

