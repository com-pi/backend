package com.example.boardservice.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
@Table(name = "sigungu")
public class Sigungu {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    public Sigungu(Long id) {
        this.id = id;
    }

    public static Sigungu ofId(Long sigunguId) {
        return new Sigungu(sigunguId);
    }
}
