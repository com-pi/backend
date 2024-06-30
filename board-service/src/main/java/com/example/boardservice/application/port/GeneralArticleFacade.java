package com.example.boardservice.application.port;

import com.example.boardservice.application.ArticleHashtagService;
import com.example.boardservice.application.GeneralArticleService;
import com.example.boardservice.application.port.in.GeneralArticleUseCase;
import com.example.boardservice.domain.ArticleHashtag;
import com.example.boardservice.domain.GeneralArticle;
import com.example.boardservice.domain.GeneralArticleCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GeneralArticleFacade implements GeneralArticleUseCase {

    private final GeneralArticleService generalArticleService;
    private final ArticleHashtagService articleHashtagService;

    @Override
    @Transactional
    public Long post(GeneralArticleCommand articleCreate, List<MultipartFile> imageFiles) {
        GeneralArticle generalArticle = generalArticleService.post(articleCreate, imageFiles);
        articleHashtagService.generateHashtags(generalArticle.getArticleId(), articleCreate.getHashtagList());
        return generalArticle.getArticleId();
    }

    @Override
    public Long update(GeneralArticleCommand articleUpdate, List<MultipartFile> imageFiles) {
        if(!articleUpdate.getHashtagList().isEmpty()) {
            articleHashtagService.deleteAllByArticleId(articleUpdate.getArticleId());
            articleHashtagService.generateHashtags(articleUpdate.getArticleId(), articleUpdate.getHashtagList());
        }
        return generalArticleService.update(articleUpdate, imageFiles);
    }

    @Override
    @Transactional
    public Long delete(GeneralArticle article) {
        articleHashtagService.deleteAllByArticleId(article.getArticleId());
        return generalArticleService.delete(article);
    }

    @Override
    public List<GeneralArticle> getGeneralArticleList(Pageable pageable) {
        Page<GeneralArticle> generalArticlePage = generalArticleService.getGeneralArticleList(pageable);
        return generalArticlePage
                .stream()
                .peek(generalArticle -> {
                    List<ArticleHashtag> articleHashtags = articleHashtagService.getHashtagListByArticle(generalArticle.getArticleId());
                    List<String> hashtagList = articleHashtags.stream()
                            .map(articleHashtag -> articleHashtag.getHashtag().getName())
                            .toList();
                    generalArticle.addHashtagList(hashtagList);
                })
                .toList();
    }

    @Override
    public GeneralArticle getGeneralArticle(Long articleId) {
        GeneralArticle generalArticle = generalArticleService.getGeneralArticle(articleId);
        List<String> hashtagList = articleHashtagService.getHashtagListByArticle(articleId)
                .stream()
                .map(articleHashtag -> articleHashtag.getHashtag().getName())
                .toList();
        generalArticle.addHashtagList(hashtagList);
        return generalArticle;
    }
}
