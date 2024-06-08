package com.example.myplant.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
