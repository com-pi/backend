package com.example.boardservice.application;

import com.example.boardservice.adapter.out.persistence.entity.DiaryArticleUseCase;
import com.example.boardservice.application.port.out.DiaryArticleCommandPort;
import com.example.boardservice.application.port.out.DiaryArticleQueryPort;
import com.example.boardservice.domain.DiaryArticle;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiaryArticleService implements DiaryArticleUseCase {

    private final DiaryArticleCommandPort diaryArticleCommandPort;
    private final DiaryArticleQueryPort diaryArticleQueryPort;

    @Override
    public Long post(DiaryArticle diaryArticle) {
        DiaryArticle savedDiaryArticle = diaryArticleCommandPort.save(diaryArticle);
        return savedDiaryArticle.getArticleId();
    }
}
