package com.example.authserver.domain;

import com.example.authserver.adapter.out.command.MemberEntity;
import com.example.authserver.application.ModifyMemberInfoCommand;
import com.example.common.domain.Address;
import com.example.common.domain.Location;
import com.example.common.domain.Role;
import com.example.common.exception.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder(toBuilder = true)
// Todo record 클래스로 변경 검토
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
    private final Location location;
    private final Address address;
    private final LocalDateTime lastLogin;

    public boolean isSocialAccount() {
        return this.kakaoId != null || this.naverId != null;
    }

    public static Member create(MemberCreate memberCreate, PasswordEncoder passwordEncoder) {
        return Member.builder()
                .kakaoId(memberCreate.getKakaoId())
                .naverId(memberCreate.getNaverId())
                .phoneNumber(memberCreate.getPhoneNumber())
                .email(memberCreate.getEmail())
                .password(passwordEncoder.encode(memberCreate.getPassword()))
                .nickname(memberCreate.getNickname())
                .role(Role.MEMBER)
                .imageUrl(memberCreate.getImageUrl())
                .thumbnailUrl(memberCreate.getThumbnailUr())
                .build();
    }

    public static Member createSocial(MemberCreate memberCreate) {
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

    public Member updateInfo(ModifyMemberInfoCommand command){
        return this.toBuilder()
                .nickname(command.nickName() == null ? nickname : command.nickName())
                .introduction(command.introduction() == null ? introduction : command.introduction())
                .imageUrl(command.isPicUploaded() ? command.profileImageUrl() : imageUrl)
                .thumbnailUrl(command.isPicUploaded() ? command.thumbnailUrl() : thumbnailUrl)
                .location(command.location())
                .address(command.address())
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

}
