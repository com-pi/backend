package com.example.boardservice.application.port.in;

import com.example.boardservice.domain.QnaArticle;
import com.example.boardservice.domain.QnaArticleCommand;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface QnaArticleUseCase {
    Long post(QnaArticleCommand command, List<MultipartFile> imageFiles);

    Long update(QnaArticleCommand command, List<MultipartFile> imageFiles);

    Long delete(QnaArticle domain);

    List<QnaArticle> getQnaArticleList(Pageable pageable);

    QnaArticle getQnaArticle(Long articleId);
}
