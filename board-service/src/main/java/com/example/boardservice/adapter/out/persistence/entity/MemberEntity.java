package com.example.boardservice.adapter.out.persistence.entity;

import com.example.boardservice.domain.Member;
import com.example.common.domain.Passport;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "member")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    private String nickname;

    private String imageUrl;


    /**
     * 생성자
     */
    public MemberEntity(Long memberId) {
        this.memberId = memberId;
    }

    /**
     *
     */
    public static MemberEntity fromPassport(Passport passport){
        return MemberEntity.builder()
                .memberId(passport.memberId())
                .nickname(passport.nickName())
                .imageUrl(passport.thumbnail())
                .build();
    }

    public Member toDomain() {
        return Member.builder()
                .memberId(this.memberId)
                .nickname(this.nickname)
                .imageUrl(this.imageUrl)
                .build();
    }

    public static MemberEntity from(Long memberId) {
        return new MemberEntity(memberId);
    }

    public void update(Passport passport){
        this.nickname = passport.nickName();
        this.imageUrl = passport.thumbnail();
    }
}