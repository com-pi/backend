package com.example.boardservice.adapter.out.persistence.entity;

import com.example.boardservice.domain.Hashtag;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Table(
        name = "hashtag",
        indexes = {
                @Index(name = "idx_name", columnList = "name")
        }
)
public class HashtagEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hashtagId;

    private String name;

    public static HashtagEntity from(Hashtag hashtag) {
        return HashtagEntity.builder()
                .hashtagId(hashtag.getHashtagId())
                .name(hashtag.getName())
                .build();
    }

    public Hashtag toDomain() {
        return Hashtag.builder()
                .hashtagId(hashtagId)
                .name(name)
                .build();
    }
}
