package com.example.myplant.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Builder
    public Character(Long id, String characterNo, String characterName, List<CharacterIllustration> illustrations ,List<PlantCharacter> plantCharacters) {
        this.id = id;
        this.characterNo = characterNo;
        this.characterName = characterName;
        this.illustrations = illustrations;
        this.plantCharacters = plantCharacters;
    }
}
