package com.example.boardservice.application;

import com.example.boardservice.application.port.out.CommonArticleQueryPort;
import com.example.boardservice.domain.Article;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommonArticleService {

    private final CommonArticleQueryPort commonArticleQueryPort;

    public Page<Article> getArticleList(Pageable pageable) {
        return commonArticleQueryPort.getArticleList(pageable);
    }

    public List<Article> getArticleListByArticleId(List<Long> articleIdList, Pageable pageable) {
        return commonArticleQueryPort.getArticleListByArticleId(articleIdList, pageable);
    }
}
