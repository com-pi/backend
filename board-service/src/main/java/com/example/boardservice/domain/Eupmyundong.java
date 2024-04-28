package com.example.boardservice.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
@Table(name = "eupmyundong")
public class Eupmyundong {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String eupmyundongName;

    public Eupmyundong(Long id) {
        this.id = id;
    }

    public static Eupmyundong ofId(Long eupmyundongId) {
        return new Eupmyundong(eupmyundongId);
    }
}
