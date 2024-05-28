package com.example.myplant.application.port.out;

import com.example.myplant.domain.Character;
import com.example.myplant.domain.Plant;
import java.util.List;
import java.util.Optional;

public interface FindPlantPort {
    Optional<Plant> findPlantById(Long id);
    List<Plant> findPlantsByMemberId(Long memberId);
    Optional<Plant> findPlantByPlantId(Long plantId);
    Optional<Character> findCharacterById(Long characterId);
}
