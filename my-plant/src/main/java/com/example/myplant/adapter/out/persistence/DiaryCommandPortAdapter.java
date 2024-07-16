package com.example.myplant.adapter.out.persistence;

import com.example.common.domain.Passport;
import com.example.common.exception.NotFoundException;
import com.example.myplant.adapter.out.persistence.entity.DiaryEntity;
import com.example.myplant.adapter.out.persistence.request.DiaryArticleRequest;
import com.example.myplant.application.port.out.DiaryCommandPort;
import com.example.myplant.domain.Diary;
import com.example.myplant.security.PassportHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DiaryCommandPortAdapter implements DiaryCommandPort {

    private final DiaryRepository diaryRepository;
    private final DiaryArticleClient diaryArticleClient;

    @Override
    public Long save(Diary diary) {
        DiaryEntity diaryEntity = diaryRepository.save(DiaryEntity.fromDomain(diary));
        return diaryEntity.toDomain().getDiaryId();
    }

    @Override
    public void update(Diary diary) {
        DiaryEntity diaryEntity = diaryRepository.findById(diary.getDiaryId())
                .orElseThrow(() -> new NotFoundException(DiaryEntity.class));
        diaryEntity.update(diary);
    }

    @Override
    public void delete(Diary diary) {
        DiaryEntity diaryEntity = diaryRepository.findById(diary.getDiaryId())
                .orElseThrow(() -> new NotFoundException(DiaryEntity.class));
        diaryEntity.delete();
    }

    @Override
    @Async
    public void postDiaryArticle(Diary diary) {
        Passport passport = PassportHolder.getPassport();
        diaryArticleClient.postDiaryArticle(
                passport,
                DiaryArticleRequest.of(diary)
        );
    }

}
