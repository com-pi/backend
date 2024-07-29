package com.example.myplant.adapter.out.persistence;

import com.example.myplant.adapter.out.persistence.entity.DiaryEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DiaryRepository extends JpaRepository<DiaryEntity, Long> {

    @Query("""
        SELECT d
        FROM DiaryEntity d
        WHERE 
          d.memberId = :memberId
          AND d.myPlantId = :myPlantId
          AND d.deletionYn = :deletionYn
    """)
    List<DiaryEntity> findByCreatedDateBetweenAndMemberIdAndMyPlantIdAndDeletionYn(
            @Param("memberId") Long memberId,
            @Param("myPlantId") Long myPlantId,
            @Param("deletionYn") String deletionYn
    );

    List<DiaryEntity> findByMemberIdAndIsPublicAndIsPublishedAndDeletionYn(Long memberId, boolean isPublic, boolean isPublished, String deletionYn, Pageable pageable);

    Optional<DiaryEntity> findByDiaryIdAndMemberIdAndDeletionYn(Long diaryId, Long memberId, String deletionYn);

    Optional<DiaryEntity> findByDiaryIdAndDeletionYn(Long diaryId, String deletionYn);

    Optional<DiaryEntity> findByDiaryIdAndIsPublishedAndIsPublicAndDeletionYn(Long diaryId, boolean isPublished, boolean isPublic, String deletionYn);

    Optional<DiaryEntity> findByMyPlantIdAndCreatedDateAndDeletionYn(Long myPlantId, LocalDate createdDate, String deletionYn);
}
