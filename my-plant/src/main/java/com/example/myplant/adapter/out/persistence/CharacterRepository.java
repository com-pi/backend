package com.example.myplant.adapter.out.persistence;

import com.example.myplant.domain.Character;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRepository extends JpaRepository<Character, Long>{
}
