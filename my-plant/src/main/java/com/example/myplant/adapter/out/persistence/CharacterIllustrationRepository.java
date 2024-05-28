package com.example.myplant.adapter.out.persistence;

import com.example.myplant.domain.CharacterIllustration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterIllustrationRepository extends JpaRepository<CharacterIllustration, Long> {
}
