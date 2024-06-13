package com.example.myplant.adapter.out.persistence;

import com.example.myplant.adapter.out.persistence.entity.MyPlantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyPlantRepository extends JpaRepository<MyPlantEntity, Long> {
}
