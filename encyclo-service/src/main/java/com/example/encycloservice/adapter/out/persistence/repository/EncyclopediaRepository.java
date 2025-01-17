package com.example.encycloservice.adapter.out.persistence.repository;

import com.example.encycloservice.adapter.out.persistence.entity.PlantAddInquiryEntity;
import com.example.encycloservice.adapter.out.persistence.entity.PlantSpeciesEntity;
import com.example.encycloservice.domain.PlantAddInquiry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EncyclopediaRepository extends JpaRepository<PlantSpeciesEntity, Long> {

    @Query("SELECT p FROM PlantSpeciesEntity p WHERE p.commonName LIKE %:namePattern%")
    Page<PlantSpeciesEntity> searchUsingNamePattern(@Param("namePattern") String namePattern, Pageable pageable);

    @Query("SELECT pai FROM PlantAddInquiryEntity pai WHERE pai.status = :status")
    Page<PlantAddInquiryEntity> findByStatusWithPaging(PlantAddInquiry.Status status, Pageable pageable);

    @Query("SELECT pai FROM PlantAddInquiryEntity pai")
    Page<PlantAddInquiryEntity> findAllWithPaging(Pageable pageable);

    @Query("SELECT pai FROM PlantAddInquiryEntity pai WHERE pai.id = :id")
    Optional<PlantAddInquiryEntity> findAddInquiryById(Long id);

    @Query("SELECT p FROM PlantSpeciesEntity p WHERE p.plantTaxonomy.genus = :genus and p.plantTaxonomy.species = :species")
    Optional<PlantSpeciesEntity> searchUsingSpecies(String genus, String species);

    boolean existsByCommonName(String commonName);

}