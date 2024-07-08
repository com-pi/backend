package com.example.boardservice.application;

import com.example.boardservice.application.port.out.ArticleHashtagCommandPort;
import com.example.boardservice.application.port.out.ArticleHashtagQueryPort;
import com.example.boardservice.application.port.out.HashtagCommandPort;
import com.example.boardservice.application.port.out.HashtagQueryPort;
import com.example.boardservice.domain.ArticleHashtag;
import com.example.boardservice.domain.Hashtag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ArticleHashtagService {

    private final ArticleHashtagCommandPort articleHashtagCommandPort;
    private final ArticleHashtagQueryPort articleHashtagQueryPort;
    private final HashtagCommandPort hashtagCommandPort;
    private final HashtagQueryPort hashtagQueryPort;

    @Transactional
    public void generateHashtags(Long articleId, List<String> hashtagNameList) {
        hashtagNameList.stream()
                .map(name -> {
                    Hashtag findedHashtag = hashtagQueryPort.findHashtagByName(name);
                    if (Objects.isNull(findedHashtag)) {
                        Hashtag hashtag = Hashtag.generateHashtag(name);
                        Hashtag savedHashtag = hashtagCommandPort.save(hashtag);
                        return ArticleHashtag.generateArticleHashtag(articleId, savedHashtag);
                    } else {
                        return ArticleHashtag.generateArticleHashtag(articleId, findedHashtag);
                    }
                })
                .forEach(articleHashtagCommandPort::save);
    }

    @Transactional
    public void deleteAllByArticleId(Long articleId) {
        articleHashtagCommandPort.deleteAllByArticleId(articleId);
    }

    public List<ArticleHashtag> getHashtagListByArticle(Long articleId) {
        return articleHashtagQueryPort.getArticleHashtagListByArticleId(articleId);
    }

    public List<ArticleHashtag> getArticleIdByHashtagId(Long hashtagId, Pageable pageable) {
        return articleHashtagQueryPort.getArticleIdByHashtagId(hashtagId, pageable);
    }

    @Nullable
    public Long getArticleIdByHashtagName(String name) {
        Hashtag hashtag = hashtagQueryPort.findHashtagByName(name);
        if(Objects.nonNull(hashtag)) {
            return hashtag.getHashtagId();
        }
        return null;
    }
}
