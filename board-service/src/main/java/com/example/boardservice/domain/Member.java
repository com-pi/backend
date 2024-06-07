package com.example.boardservice.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Member {

    private Long memberId;

    private String nickname;

    private String imageUrl;

    /**
     * 생성자
     */
    @Builder
    public Member(Long memberId, String nickname, String imageUrl) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.imageUrl = imageUrl;
    }

    private Member(Long memberId) {
        this.memberId = memberId;
    }

    /**
     *
     */
    public static Member ofId(Long authorId) {
        return new Member(authorId);
    }
}
