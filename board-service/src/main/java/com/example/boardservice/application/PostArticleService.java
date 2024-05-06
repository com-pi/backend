package com.example.boardservice.application;

import com.example.boardservice.application.port.in.PostArticleUseCase;
import com.example.boardservice.application.port.in.PostBuyAndSellCommand;
import com.example.boardservice.application.port.out.ImageCommandPort;
import com.example.boardservice.application.port.out.PostArticlePort;
import com.example.boardservice.domain.Area;
import com.example.boardservice.domain.ArticleType;
import com.example.boardservice.domain.BuyAndSell;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostArticleService implements PostArticleUseCase {

    private final ImageCommandPort imageCommandPort;
    private final PostArticlePort postArticlePort;

    @Override
    @Transactional
    public void postBuyAndSell(PostBuyAndSellCommand command) {

        List<String> imageUrls = imageCommandPort.saveImages(command.getImageFiles());

        BuyAndSell newPost = BuyAndSell.generate()
                .articleType(ArticleType.BuyAndSell)
                .price(command.getPrice())
                .title(command.getTitle())
                .hashtags(command.getHashtags())
                .imageUrls(imageUrls)
                .area(Area.of(command.getSidoId(), command.getSigunguId(), command.getEupmyundongId()))
                .content(command.getContent())
                .build();

        postArticlePort.postBuyAndSell(newPost);
    }

}
