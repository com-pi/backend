package com.example.myplant.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Character {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String characterNo;
    private String characterName;

    @OneToMany(mappedBy = "character")
    private List<CharacterIllustration> illustrations;

    @OneToMany(mappedBy = "character")
    private List<PlantCharacter> plantCharacters;
}
