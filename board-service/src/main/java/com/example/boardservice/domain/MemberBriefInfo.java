package com.example.boardservice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberBriefInfo {
    private Long id;
    private String nickname;
    private String imageUrl;
    private String thumbnailUrl;

    public Member toMember() {
        return Member.builder()
                .memberId(this.id)
                .nickname(this.nickname)
                .imageUrl(this.imageUrl)
                .thumbnailUrl(this.thumbnailUrl)
                .build();
    }
}
