package com.example.myplant.adapter.out.persistence;

import com.example.myplant.domain.PlantDiary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantDiaryRepository extends JpaRepository<PlantDiary, Long> {
}
