package com.example.boardservice.application.port.in;

import com.example.boardservice.domain.GeneralArticle;
import com.example.boardservice.domain.GeneralArticleCommand;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface GeneralArticleUseCase {

    Long post(GeneralArticleCommand articleCreate, List<MultipartFile> imageFiles);

    Long update(GeneralArticleCommand domain, List<MultipartFile> imageFiles);

    Long delete(GeneralArticle article);

    List<GeneralArticle> getGeneralArticleList(Pageable pageable);

    GeneralArticle getGeneralArticle(Long articleId);
}
