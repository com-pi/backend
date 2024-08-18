package com.example.boardservice.domain;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member {

    private Long memberId;

    private String nickname;

    private String imageUrl;

    private String thumbnailUrl;


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
