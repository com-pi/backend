package com.example.boardservice.adapter.out.persistence;

import com.example.boardservice.adapter.out.persistence.entity.DiaryArticleEntity;
import com.example.boardservice.application.port.out.DiaryArticleCommandPort;
import com.example.boardservice.domain.DiaryArticle;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DiaryArticleCommandAdapter implements DiaryArticleCommandPort {

    private final DiaryArticleRepository diaryArticleRepository;

    @Override
    public DiaryArticle save(DiaryArticle diaryArticle) {
        DiaryArticleEntity diaryArticleEntity = diaryArticleRepository.save(DiaryArticleEntity.fromDomain(diaryArticle));
        return diaryArticleEntity.toDomain();
    }

}
