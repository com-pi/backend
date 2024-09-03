package com.example.boardservice.application;

import com.example.boardservice.adapter.out.persistence.entity.DiaryArticleUseCase;
import com.example.boardservice.domain.DiaryArticle;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DiaryArticleFacade implements DiaryArticleUseCase {

    private final DiaryArticleService diaryArticleService;
    private final ArticleHashtagService articleHashtagService;

    @Override
    @Transactional
    public Long post(DiaryArticle articleCreate) {
        DiaryArticle diaryArticle = diaryArticleService.post(articleCreate);
        articleHashtagService.generateHashtags(diaryArticle.getArticleId(), articleCreate.getHashtagList());
        return diaryArticle.getArticleId();
    }
}
