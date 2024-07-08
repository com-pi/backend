package com.example.boardservice.adapter.out.persistence;

import com.example.boardservice.application.port.out.DiaryArticleQueryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DiaryArticleQueryAdapter implements DiaryArticleQueryPort {

    private final DiaryArticleRepository diaryArticleRepository;

}
