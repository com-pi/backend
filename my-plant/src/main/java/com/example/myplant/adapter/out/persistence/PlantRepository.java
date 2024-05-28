package com.example.myplant.adapter.out.persistence;

import com.example.myplant.domain.Character;
import com.example.myplant.domain.Plant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlantRepository extends JpaRepository<Plant, Long> {
    List<Plant> findByMemberId(Long memberId);
    Optional<Plant> findByPlantId(Long plantId);
    Optional<Character> findByCharacterId(Long characterId);
}
