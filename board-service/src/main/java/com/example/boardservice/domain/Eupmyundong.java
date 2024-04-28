package com.example.boardservice.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
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
