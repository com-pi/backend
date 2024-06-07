package com.example.encycloservice.adapter.out.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EncyclopediaRepository extends JpaRepository<PlantSpeciesEntity, Long> {

    @Query("SELECT p FROM PlantSpeciesEntity p WHERE p.commonName LIKE %:namePattern%")
    Page<PlantSpeciesEntity> searchUsingNamePattern(@Param("namePattern") String namePattern, Pageable pageable);

    @Query("SELECT p FROM PlantSpeciesEntity p WHERE p.commonName = :name")
    Optional<PlantSpeciesEntity> findByName(@Param("name") String name);

    boolean existsByCommonName(String commonName);

}