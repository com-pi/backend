package com.example.encycloservice.adapter.out.persistence.repository;

import com.example.encycloservice.adapter.out.persistence.entity.EncyclopediaPlantEntity;
import com.example.encycloservice.adapter.out.persistence.entity.MyEncyclopediaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MyEncyclopediaRepository extends JpaRepository<MyEncyclopediaEntity, Long> {

    @Query("SELECT DISTINCT e FROM MyEncyclopediaEntity e LEFT JOIN FETCH e.plantCollection WHERE e.memberId = :memberId")
    List<MyEncyclopediaEntity> findListByMemberId(Long memberId);

    @Modifying
    @Query("DELETE FROM EncyclopediaPlantEntity e WHERE e.plantEntity.id = :plantId AND e.myEncyclopediaEntity.id = :encyclopediaId")
    void deleteByPlantSpeciesIdAndMyEncyclopediaId(Long plantId, Long encyclopediaId);

    @Query("SELECT e FROM MyEncyclopediaEntity e " +
            "LEFT JOIN FETCH e.plantCollection p LEFT JOIN FETCH p.plantEntity " +
            "WHERE e.id = :myEncyclopediaId")
    Optional<MyEncyclopediaEntity> findEncyclopediaById(Long myEncyclopediaId);

    @Query("DELETE FROM EncyclopediaPlantEntity e WHERE e.myEncyclopediaEntity.id = :myEncyclopediaId")
    @Modifying
    void deletePlantsFromEncyclopedia(Long myEncyclopediaId);

    @Query("SELECT ep FROM EncyclopediaPlantEntity ep WHERE ep.myEncyclopediaEntity.id = :myEncyclopediaId")
    Page<EncyclopediaPlantEntity> findPlantListByEncyclopediaId(Long myEncyclopediaId, PageRequest pageable);

}
