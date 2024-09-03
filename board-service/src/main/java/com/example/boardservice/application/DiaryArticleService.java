package com.example.boardservice.application;

import com.example.boardservice.application.port.out.DiaryArticleCommandPort;
import com.example.boardservice.application.port.out.DiaryArticleQueryPort;
import com.example.boardservice.domain.DiaryArticle;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiaryArticleService {

    private final DiaryArticleCommandPort diaryArticleCommandPort;
    private final DiaryArticleQueryPort diaryArticleQueryPort;

    public DiaryArticle post(DiaryArticle diaryArticle) {
        return diaryArticleCommandPort.save(diaryArticle);
    }
}
