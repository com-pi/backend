package com.example.myplant.adapter.out.persistence.entity;

import com.example.myplant.domain.PlantCharacter;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "plant_character")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlantCharacterEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long plantCharacterId;

    private String imageUrl;

    private String characterName;

    /**
     * 생성자
     */
    @Builder
    public PlantCharacterEntity(Long plantCharacterId, String imageUrl, String characterName) {
        this.plantCharacterId = plantCharacterId;
        this.imageUrl = imageUrl;
        this.characterName = characterName;
    }

    public PlantCharacter toDomain() {
        return PlantCharacter.builder()
                .plantCharacterId(this.plantCharacterId)
                .imageUrl(this.imageUrl)
                .characterName(this.characterName)
                .build();
    }

    public static PlantCharacterEntity ofId(Long plantCharacterId) {
        return PlantCharacterEntity.builder()
                .plantCharacterId(plantCharacterId)
                .build();
    }
}
