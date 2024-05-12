package com.example.boardservice.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "member_board")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String member_id;
    private String nickname;
    private String imageUrl;

    private Member(Long id) {
        this.id = id;
    }

    public static Member ofId(Long authorId){
        return new Member(authorId);
    }

}
