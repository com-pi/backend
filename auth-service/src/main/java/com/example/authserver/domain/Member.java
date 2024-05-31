package com.example.authserver.domain;

import com.example.authserver.adapter.out.MemberEntity;
import com.example.common.domain.Address;
import com.example.common.domain.Role;
import com.example.common.exception.NotFoundException;
import com.example.imagemodule.domain.ImageAndThumbnail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.locationtech.jts.geom.Point;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder(toBuilder = true)
public class Member {
    private final Long id;
    private final String kakaoId;
    private final String naverId;
    private final String email;
    private final String phoneNumber;
    private final String password;
    private final Role role;
    private final String nickname;
    private final String introduction;
    private final String imageUrl;
    private final String thumbnailUrl;
    private final Point location;
    private final Address address;
    private final LocalDateTime lastLogin;

    public boolean isSocialAccount() {
        return this.kakaoId != null || this.naverId != null;
    }

    public static Member create(MemberCreate memberCreate) {
        return Member.builder()
                .kakaoId(memberCreate.getKakaoId())
                .naverId(memberCreate.getNaverId())
                .phoneNumber(memberCreate.getPhoneNumber())
                .email(memberCreate.getEmail())
                .password(memberCreate.getPassword())
                .nickname(memberCreate.getNickname())
                .role(Role.MEMBER)
                .imageUrl(memberCreate.getImageUrl())
                .thumbnailUrl(memberCreate.getThumbnailUr())
                .build();
    }

    public void authenticateWithPassword(String password, PasswordEncoder encoder) {
        if(!encoder.matches(password, this.password)){
            throw new NotFoundException(MemberEntity.class);
        }
    }

    public Member updateInfo(String nickname, String introduction){
        return this.toBuilder()
                .nickname(nickname)
                .introduction(introduction)
                .build();
    }

    public Member loginStamp(LocalDateTime loginTime){
        return this.toBuilder()
                .lastLogin(loginTime)
                .build();
    }

    public Member changePassword(String newPassword, PasswordEncoder encoder){
        return this.toBuilder()
                .password(encoder.encode(newPassword))
                .build();
    }

    public Member deleteMember(){
        // Todo 정책 다시 생각해보기
        // 다시 가입할 수 있도록 처리
        // 닉네임은 다시 못쓰도록 함
        return this.toBuilder()
                .kakaoId(null)
                .naverId(null)
                .email(null)
                .phoneNumber(null)
                .build();
    }

    public Member updateProfileImage(ImageAndThumbnail imageAndThumbnail) {
        return this.toBuilder()
                .imageUrl(imageAndThumbnail.imageUrl())
                .imageUrl(imageAndThumbnail.thumbnailUrl())
                .build();
    }
}
