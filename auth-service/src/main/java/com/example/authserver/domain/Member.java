package com.example.authserver.domain;

import com.example.authserver.application.port.out.external.KakaoUserInfoResponse;
import com.example.authserver.application.port.out.external.NaverUserInfoResponse;
import com.example.common.DeletedAtAbstractEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.geo.Point;

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

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "kakao_id", unique = true)
    private String kakaoId;

    @Column(name = "naver_id", unique = true)
    private String naverId;

    @Column(unique = true)
    private String email;
    private String password;

    @Column(unique = true)
    private String nickname;
    private String imageUrl;
    private String thumbnailUrl;

//    @Column(columnDefinition = "Point") d
    private Point location;

    public void updateProfileFromSocialProfile(KakaoUserInfoResponse.KakaoProfile kakaoProfile) {
        this.imageUrl = kakaoProfile.profile_image_url();
        this.thumbnailUrl = kakaoProfile.thumbnail_image_url();
    }

    public void updateProfileFromSocialProfile(NaverUserInfoResponse.NaverProfile naverProfile) {
        this.imageUrl = naverProfile.profile_image();
    }

    public static Member newMemberForKakaoUser(KakaoUserInfoResponse kakaoUserInfo) {
        return Member.builder()
                .kakaoId(kakaoUserInfo.getId().toString())
                .nickname(
                        kakaoUserInfo.getKakao_account().profile().nickname() == null ?
                                "새회원_" + UUID.randomUUID() : kakaoUserInfo.getKakao_account().profile().nickname())
                .imageUrl(kakaoUserInfo.getKakao_account().profile().profile_image_url())
                .thumbnailUrl(kakaoUserInfo.getKakao_account().profile().thumbnail_image_url())
                .build();
    }

    public static Member newMemberForNaverUser(NaverUserInfoResponse naverUserInfo) {
        return Member.builder()
                .naverId(naverUserInfo.getResponse().id())
                .nickname(
                        naverUserInfo.getResponse().nickname() == null ?
                                "새회원_" + UUID.randomUUID() : naverUserInfo.getResponse().nickname())
                .email(naverUserInfo.getResponse().email())
                .imageUrl(naverUserInfo.getResponse().profile_image())
                .build();
    }

    public void deleteMember(){
        kakaoId = null;
        naverId = null;
        email = null;
        super.delete();
    }

}
