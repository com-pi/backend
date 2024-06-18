package com.example.myplant.adapter.out.persistence;

import com.example.common.exception.NotFoundException;
import com.example.myplant.adapter.out.persistence.entity.DiaryEntity;
import com.example.myplant.application.port.out.DiaryQueryPort;
import com.example.myplant.domain.Diary;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DiaryQueryPortAdapter implements DiaryQueryPort {

    private final DiaryRepository diaryRepository;

    @Override
    public Diary getDiaryByDiaryId(Long diaryId) {
        DiaryEntity diaryEntity = diaryRepository.findByDiaryIdAndDeletionYn(diaryId, "N")
                .orElseThrow(() -> new NotFoundException(DiaryEntity.class));
        return diaryEntity.toDomain();
    }

    @Override
    public List<Diary> getDiaryStatus(LocalDate startDate, LocalDate endDate, Long memberId) {
        List<DiaryEntity> diaryEntityList = diaryRepository.findByCreatedDateBetweenAndMemberIdAndDeletionYn(startDate, endDate, memberId, "N");
        return diaryEntityList.stream()
                .map(DiaryEntity::toDomain)
                .toList();
    }

    @Override
    public List<Diary> getDiaryByMemberId(Long memberId, Pageable pageable) {
        List<DiaryEntity> diaryEntityList = diaryRepository.findByMemberIdAndIsPublicAndIsPublishedAndDeletionYn(memberId, true, true, "N", pageable);
        return diaryEntityList.stream()
                .map(DiaryEntity::toDomain)
                .toList();
    }

    @Override
    public Diary getPersonalDiaryByDiaryId(Long diaryId, Long memberId) {
        DiaryEntity diaryEntity = diaryRepository.findByDiaryIdAndMemberIdAndDeletionYn(diaryId, memberId, "N")
                .orElseThrow(() -> new NotFoundException(DiaryEntity.class));
        return diaryEntity.toDomain();
    }

    @Override
    public Diary getPublicDiaryByDiaryId(Long diaryId) {
        DiaryEntity diaryEntity = diaryRepository.findByDiaryIdAndIsPublishedAndIsPublicAndDeletionYn(diaryId, true, true, "N")
                .orElseThrow(() -> new NotFoundException(DiaryEntity.class));
        return diaryEntity.toDomain();
    }
}