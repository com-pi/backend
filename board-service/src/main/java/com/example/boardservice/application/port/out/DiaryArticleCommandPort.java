package com.example.boardservice.application.port.out;

import com.example.boardservice.domain.DiaryArticle;

public interface DiaryArticleCommandPort {
    DiaryArticle save(DiaryArticle diaryArticle);
}
