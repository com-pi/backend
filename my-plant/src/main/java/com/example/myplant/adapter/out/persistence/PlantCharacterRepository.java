package com.example.myplant.adapter.out.persistence;

import com.example.myplant.domain.PlantCharacter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantCharacterRepository extends JpaRepository<PlantCharacter, Long> {
}
