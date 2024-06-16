package com.example.myplant.adapter.out.persistence;

import com.example.myplant.adapter.out.persistence.entity.DiaryEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DiaryRepository extends JpaRepository<DiaryEntity, Long> {

    List<DiaryEntity> findByCreatedDateBetweenAndMemberIdAndDeletionYn(LocalDate startDate, LocalDate endDate, Long memberId, String deletionYn);

    List<DiaryEntity> findByMemberIdAndIsPublicAndIsPublishedAndDeletionYn(Long memberId, boolean isPublic, boolean isPublished, String deletionYn, Pageable pageable);

    Optional<DiaryEntity> findByDiaryIdAndMemberIdAndDeletionYn(Long diaryId, Long memberId, String deletionYn);

    Optional<DiaryEntity> findByDiaryIdAndDeletionYn(Long diaryId, String deletionYn);

    Optional<DiaryEntity> findByDiaryIdAndIsPublishedAndIsPublicAndDeletionYn(Long diaryId, boolean isPublished, boolean isPublic, String deletionYn);
}
