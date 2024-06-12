package com.example.authserver.adapter.out.command;

import com.example.authserver.domain.Member;
import com.example.authserver.util.GeomUtil;
import com.example.common.baseentity.DeletedAtAbstractEntity;
import com.example.common.domain.Address;
import com.example.common.domain.Role;
import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Entity
@Table(name = "MEMBER")
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@ToString(exclude = {"location"})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@EntityListeners(MemberEntityListener.class)
public class MemberEntity extends DeletedAtAbstractEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "kakao_id", unique = true, updatable = false)
    private String kakaoId;

    @Column(name = "naver_id", unique = true, updatable = false)
    private String naverId;

    @Column(unique = true, updatable = false)
    private String email;
    @Column(unique = true, updatable = false)
    private String phoneNumber;

    private String password;

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

    public static MemberEntity fromDomain(Member member) {
        return MemberEntity.builder()
                .id(member.getId())
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
                .location(GeomUtil.createPoint(member.getLocation()))
                .address(member.getAddress())
                .lastLogin(member.getLastLogin())
                .build();
    }


    public static Member toDomain(MemberEntity memberEntity) {
        return Member.builder()
                .id(memberEntity.getId())
                .kakaoId(memberEntity.getKakaoId())
                .naverId(memberEntity.getNaverId())
                .email(memberEntity.getEmail())
                .phoneNumber(memberEntity.getPhoneNumber())
                .password(memberEntity.getPassword())
                .role(memberEntity.getRole())
                .nickname(memberEntity.getNickname())
                .introduction(memberEntity.getIntroduction())
                .imageUrl(memberEntity.getImageUrl())
                .thumbnailUrl(memberEntity.getThumbnailUrl())
                .location(GeomUtil.createLocation(memberEntity.getLocation()))
                .address(memberEntity.getAddress())
                .lastLogin(memberEntity.getLastLogin())
                .build();
    }

    public void update(Member member){
        email = member.getEmail();
        phoneNumber = member.getPhoneNumber();
        password = member.getPassword();
        role = member.getRole();
        nickname = member.getNickname();
        introduction = member.getIntroduction();
        imageUrl = member.getImageUrl();
        thumbnailUrl = member.getThumbnailUrl();
        location = GeomUtil.createPoint(member.getLocation());
        address = member.getAddress();
        lastLogin = member.getLastLogin();
    }
}
