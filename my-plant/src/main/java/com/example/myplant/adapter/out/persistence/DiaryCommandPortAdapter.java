package com.example.myplant.adapter.out.persistence;

import com.example.common.exception.NotFoundException;
import com.example.myplant.adapter.out.persistence.entity.DiaryEntity;
import com.example.myplant.adapter.out.persistence.request.DiaryArticleRequest;
import com.example.myplant.application.port.out.DiaryCommandPort;
import com.example.myplant.application.port.out.MyPlantQueryPort;
import com.example.myplant.domain.Diary;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DiaryCommandPortAdapter implements DiaryCommandPort {

    private final DiaryRepository diaryRepository;
    private final DiaryArticleClient diaryArticleClient;
    private final MyPlantQueryPort myPlantQueryPort;

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
    @Transactional
    public void postDiaryArticle(Diary diary) {
//        Passport passport = PassportHolder.getPassport();
        String plantType = myPlantQueryPort.getPlantType(diary.getMyPlantId());
        diaryArticleClient.postDiaryArticle(
                DiaryArticleRequest.of(diary, plantType)
        );
    }

}
