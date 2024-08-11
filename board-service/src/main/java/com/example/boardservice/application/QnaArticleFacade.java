package com.example.boardservice.application;

import com.example.boardservice.application.port.in.QnaArticleUseCase;
import com.example.boardservice.domain.ArticleHashtag;
import com.example.boardservice.domain.QnaArticle;
import com.example.boardservice.domain.QnaArticleCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
@RequiredArgsConstructor
public class QnaArticleFacade implements QnaArticleUseCase {
    
    private final QnaArticleService qnaArticleService;
    private final ArticleHashtagService articleHashtagService;
    
    @Override
    @Transactional
    public Long post(QnaArticleCommand command, List<MultipartFile> imageFiles) {
        QnaArticle qnaArticle = qnaArticleService.post(command, imageFiles);
        articleHashtagService.generateHashtags(qnaArticle.getArticleId(), command.getHashtagList());
        return qnaArticle.getArticleId();
    }

    @Override
    @Transactional
    public Long update(QnaArticleCommand command, List<MultipartFile> imageFiles) {
        if(!command.getHashtagList().isEmpty()) {
            articleHashtagService.deleteAllByArticleId(command.getArticleId());
            articleHashtagService.generateHashtags(command.getArticleId(), command.getHashtagList());
        }
        return qnaArticleService.update(command, imageFiles);
    }

    @Override
    public Long delete(QnaArticle article) {
        articleHashtagService.deleteAllByArticleId(article.getArticleId());
        return qnaArticleService.delete(article);
    }

    @Override
    public List<QnaArticle> getQnaArticleList(Pageable pageable) {
        Page<QnaArticle> qnaArticlePage = qnaArticleService.getQnaArticleList(pageable);
        return qnaArticlePage
                .stream()
                .peek(qnaArticle -> {
                    List<ArticleHashtag> articleHashtags = articleHashtagService.getHashtagListByArticle(qnaArticle.getArticleId());
                    List<String> hashtagList = articleHashtags.stream()
                            .map(articleHashtag -> articleHashtag.getHashtag().getName())
                            .toList();
                    qnaArticle.addHashtagList(hashtagList);
                })
                .toList();
    }

    @Override
    public QnaArticle getQnaArticle(Long articleId) {
        QnaArticle qnaArticle = qnaArticleService.getQnaArticle(articleId);
        List<String> hashtagList = articleHashtagService.getHashtagListByArticle(articleId)
                .stream()
                .map(articleHashtag -> articleHashtag.getHashtag().getName())
                .toList();
        qnaArticle.addHashtagList(hashtagList);
        return qnaArticle;
    }
}
