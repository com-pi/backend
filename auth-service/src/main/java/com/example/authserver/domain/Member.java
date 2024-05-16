package com.example.authserver.domain;

import com.example.authserver.adapter.in.JoinRequest;
import com.example.authserver.application.port.out.external.KakaoUserInfoResponse;
import com.example.authserver.application.port.out.external.NaverUserInfoResponse;
import com.example.common.baseentity.DeletedAtAbstractEntity;
import com.example.common.domain.Address;
import com.example.common.domain.Role;
import com.example.common.exception.NotFoundException;
import com.example.imagemodule.domain.ImageAndThumbnail;
import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Point;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "MEMBER")
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@ToString(exclude = {"location"})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Member extends DeletedAtAbstractEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "kakao_id", unique = true, updatable = false)
    private String kakaoId;

    @Column(name = "naver_id", unique = true, updatable = false)
    private String naverId;

    @Column(unique = true, updatable = false)
    private String email;
    private String password;
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(unique = true, nullable = false)
    private String nickname;
    private String introduction;
    private String imageUrl;
    private String thumbnailUrl;
    private Point location;

    @Embedded
    private Address address;
    private LocalDateTime lastLogin;

    public void updateFromSocialProfile(KakaoUserInfoResponse.KakaoProfile kakaoProfile) {
        this.imageUrl = kakaoProfile.profile_image_url();
        this.thumbnailUrl = kakaoProfile.thumbnail_image_url();
    }

    public void updateFromSocialProfile(NaverUserInfoResponse.NaverProfile naverProfile) {
        this.imageUrl = naverProfile.profile_image();
    }

    public boolean isSocialAccount() {
        return this.kakaoId != null || this.naverId != null;
    }

    public static Member newMemberForKakaoUser(KakaoUserInfoResponse kakaoUserInfo) {
        return Member.builder()
                .kakaoId(kakaoUserInfo.getId().toString())
                .nickname(
                        kakaoUserInfo.getKakao_account().profile().nickname() == null ?
                                "새회원_" + UUID.randomUUID() : kakaoUserInfo.getKakao_account().profile().nickname())
                .role(Role.MEMBER)
                .imageUrl(kakaoUserInfo.getKakao_account().profile().profile_image_url())
                .thumbnailUrl(kakaoUserInfo.getKakao_account().profile().thumbnail_image_url())
                .build();
    }

    public static Member newMemberForNaverUser(NaverUserInfoResponse naverUserInfo) {
        return Member.builder()
                .naverId(naverUserInfo.getResponse().id())
                .nickname(naverUserInfo.getResponse().nickname() == null ?
                                "새회원_" + UUID.randomUUID() : naverUserInfo.getResponse().nickname())
                .email(naverUserInfo.getResponse().email())
                .role(Role.MEMBER)
                .imageUrl(naverUserInfo.getResponse().profile_image())
                .build();
    }

    public static Member newMemberFromRequest(JoinRequest joinRequest, PasswordEncoder encoder) {
        return Member.builder()
                .email(joinRequest.email())
                .password(encoder.encode(joinRequest.password()))
                .nickname("새회원_" + UUID.randomUUID())
                .phoneNumber(joinRequest.phoneNumber())
                .role(Role.MEMBER)
                .build();
    }

    public void authenticateWithPassword(String password, PasswordEncoder encoder) {
        if(!encoder.matches(password, this.password)){
            throw new NotFoundException(Member.class);
        }
    }

    public void updateInfo(String nickname, String introduction){
        this.nickname = nickname;
        this.introduction = introduction;
    }

    public LocalDateTime loginStamp(){
        // LocalDateTime 은 불변 객체
        LocalDateTime lastLoginTime = lastLogin;
        lastLogin = LocalDateTime.now();
        return lastLoginTime;
    }

    public void deleteMember(){
        // Todo 정책 다시 생각해보기
        // 다시 가입할 수 있도록 처리
        // 닉네임은 다시 못쓰도록 함
        kakaoId = null;
        naverId = null;
        email = null;
        super.delete();
    }


    public void updateProfileImage(ImageAndThumbnail imageAndThumbnail) {
        this.imageUrl = imageAndThumbnail.imageUrl();
        this.thumbnailUrl = imageAndThumbnail.thumbnailUrl();
    }
}
