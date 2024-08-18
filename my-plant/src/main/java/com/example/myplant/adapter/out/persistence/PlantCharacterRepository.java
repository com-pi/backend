package com.example.myplant.adapter.out.persistence;

import com.example.myplant.adapter.out.persistence.entity.PlantCharacterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantCharacterRepository extends JpaRepository<PlantCharacterEntity, Long> {
}
