package com.example.boardservice.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Sido {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    public Sido(Long id) {
        this.id = id;
    }

    public static Sido ofId(Long sidoId) {
        return new Sido(sidoId);
    }
}
