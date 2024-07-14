package com.example.boardservice.application;

import com.example.boardservice.application.port.out.GeneralArticleCommandPort;
import com.example.boardservice.application.port.out.GeneralArticleQueryPort;
import com.example.boardservice.domain.GeneralArticle;
import com.example.boardservice.domain.GeneralArticleCommand;
import com.example.common.exception.UnauthorizedException;
import com.example.imagemodule.application.port.ImageCommand;
import com.example.imagemodule.application.port.SaveImagesCommand;
import com.example.imagemodule.domain.MinioBucket;
import com.example.imagemodule.util.ObjectUrlMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

import static com.example.boardservice.domain.ArticleType.GENERAL_BOARD;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GeneralArticleService {

    private final GeneralArticleCommandPort generalArticleCommandPort;
    private final GeneralArticleQueryPort generalArticleQueryPort;
    private final ImageCommand imageCommand;
    private final ObjectUrlMapper objectUrlMapper;

    public GeneralArticle post(GeneralArticleCommand articleCreate, List<MultipartFile> imageFiles) {
        if(!CollectionUtils.isEmpty(imageFiles)) {
            articleCreate.updateImageUrls(getImageUrls(imageFiles));
        }
        return generalArticleCommandPort.save(articleCreate.toDomain());
    }

    public Long update(GeneralArticleCommand articleUpdate, List<MultipartFile> imageFiles) {
        GeneralArticle originArticle = generalArticleQueryPort.getArticle(articleUpdate.getArticleId());
        validatePermission(originArticle.getMemberId(), articleUpdate.getMemberId());
        articleUpdate.updateImageUrls(
                CollectionUtils.isEmpty(imageFiles) ? originArticle.getImageUrls() : getImageUrls(imageFiles)
        );
        generalArticleCommandPort.update(articleUpdate.toDomain());
        return articleUpdate.getArticleId();
    }

    public Long delete(GeneralArticle article) {
        GeneralArticle originArticle = generalArticleQueryPort.getArticle(article.getArticleId());
        validatePermission(originArticle.getMemberId(), article.getMemberId());
        generalArticleCommandPort.delete(article);
        return article.getArticleId();
    }

    public Page<GeneralArticle> getGeneralArticleList(Pageable pageable) {
        return generalArticleQueryPort.getArticleList(pageable);
    }

    public GeneralArticle getGeneralArticle(Long articleId) {
        return generalArticleQueryPort.getArticle(articleId);
    }

    /**
     * private
     */
    private List<String> getImageUrls (List<MultipartFile> imageFiles) {
        List<String> objectNames = imageCommand.saveImages(
                new SaveImagesCommand(imageFiles, GENERAL_BOARD.getBucket())
        );
        return objectUrlMapper.toUrl(objectNames, MinioBucket.ARTICLE_TRADE);
    }

    private void validatePermission(final Long originMemberId, final Long memberId) {
        if(!Objects.equals(originMemberId, memberId)) {
            throw new UnauthorizedException("글을 수정하거나 삭제할 권한이 없습니다.");
        }
    }
}