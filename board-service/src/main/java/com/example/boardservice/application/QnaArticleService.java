package com.example.boardservice.application;

import com.example.boardservice.application.port.out.QnaArticleCommandPort;
import com.example.boardservice.application.port.out.QnaArticleQueryPort;
import com.example.boardservice.domain.QnaArticle;
import com.example.boardservice.domain.QnaArticleCommand;
import com.example.common.exception.UnauthorizedException;
import com.example.imagemodule.application.port.ImageCommand;
import com.example.imagemodule.application.port.SaveImagesCommand;
import com.example.imagemodule.domain.MinioBucket;
import com.example.imagemodule.util.ObjectUrlMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import static com.example.boardservice.domain.ArticleType.QNA_BOARD;

@Service
@RequiredArgsConstructor
public class QnaArticleService {

    private final QnaArticleCommandPort qnaArticleCommandPort;
    private final QnaArticleQueryPort qnaArticleQueryPort;
    private final ImageCommand imageCommand;
    private final ObjectUrlMapper objectUrlMapper;
    
    public QnaArticle post(QnaArticleCommand command, List<MultipartFile> imageFiles) {
        if(!CollectionUtils.isEmpty(imageFiles)) {
            command.updateImageUrls(getImageUrls(imageFiles));
        }
        return qnaArticleCommandPort.save(command.toDomain());
    }

    public Long update(QnaArticleCommand command, List<MultipartFile> imageFiles) {
        QnaArticle originArticle = qnaArticleQueryPort.getArticle(command.getArticleId());
        validatePermission(originArticle.getMemberId(), command.getMemberId());
        command.updateImageUrls(
                CollectionUtils.isEmpty(imageFiles) ? originArticle.getImageUrls() : getImageUrls(imageFiles)
        );
        qnaArticleCommandPort.update(command.toDomain());
        return command.getArticleId();
    }

    public Long delete(QnaArticle article) {
        QnaArticle originArticle = qnaArticleQueryPort.getArticle(article.getArticleId());
        validatePermission(originArticle.getMemberId(), article.getMemberId());
        qnaArticleCommandPort.delete(article);
        return article.getArticleId();
    }

    public Page<QnaArticle> getQnaArticleList(Pageable pageable) {
        return qnaArticleQueryPort.getArticleList(pageable);
    }

    public QnaArticle getQnaArticle(Long articleId) {
        return qnaArticleQueryPort.getArticle(articleId);
    }

    /**
     * private
     */
    private List<String> getImageUrls (List<MultipartFile> imageFiles) {
        List<String> objectNames = imageCommand.saveImages(
                new SaveImagesCommand(imageFiles, QNA_BOARD.getBucket())
        );
        return objectUrlMapper.toUrl(objectNames, MinioBucket.ARTICLE_TRADE);
    }

    private void validatePermission(final Long originMemberId, final Long memberId) {
        if(!Objects.equals(originMemberId, memberId)) {
            throw new UnauthorizedException("글을 수정하거나 삭제할 권한이 없습니다.");
        }
    }
}
