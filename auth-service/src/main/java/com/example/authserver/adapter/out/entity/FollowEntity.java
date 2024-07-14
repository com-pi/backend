package com.example.authserver.adapter.out.entity;

import com.example.authserver.domain.Follow;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "FOLLOW")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FollowEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "follower_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private MemberEntity follower;

    @JoinColumn(name = "following_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private MemberEntity followee;

    public static FollowEntity fromDomain(Follow follow, EntityManager em) {
        return FollowEntity.builder()
                .follower(em.getReference(MemberEntity.class, follow.follower().getId()))
                .followee(em.getReference(MemberEntity.class, follow.followee().getId()))
                .build();
    }

}
