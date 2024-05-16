package com.example.myplant.adapter.out.persistence;

import com.example.myplant.domain.Plant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantRepository extends JpaRepository<Plant, Long> {
}
