package com.example.boardservice.domain;

import com.example.common.domain.Passport;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "member")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long memberId;
    private String nickname;
    private String imageUrl;

    private Member(Long id) {
        this.id = id;
    }

    public static Member ofId(Long authorId){
        return new Member(authorId);
    }

    public static Member fromPassport(Passport passport){
        return Member.builder()
                .memberId(passport.memberId())
                .nickname(passport.nickName())
                .imageUrl(passport.thumbnail())
                .build();
    }

    public void update(Passport passport){
        this.nickname = passport.nickName();
        this.imageUrl = passport.thumbnail();
    }

}
