package com.example.boardservice.application;

import com.example.boardservice.adapter.in.web.command.PostBuyAndSellCommand;
import com.example.boardservice.adapter.in.web.command.UpdateBuyAndSellCommand;
import com.example.boardservice.adapter.out.persistence.BuyAndSellRepository;
import com.example.boardservice.application.port.in.ArticleUseCase;
import com.example.boardservice.application.port.out.ArticleQueryPort;
import com.example.boardservice.application.port.out.PostArticlePort;
import com.example.boardservice.domain.BuyAndSell;
import com.example.common.exception.NotFoundException;
import com.example.imagemodule.application.port.ImageCommand;
import com.example.imagemodule.application.port.SaveImagesCommand;
import com.example.imagemodule.domain.MinioBucket;
import com.example.imagemodule.util.ObjectUrlMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.boardservice.domain.ArticleType.BUY_AND_SELL;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleService implements ArticleUseCase {

    private final ImageCommand imageCommand;
    private final PostArticlePort postArticlePort;
    private final ArticleQueryPort articleQueryPort;
    private final ObjectUrlMapper objectUrlMapper;
    private final BuyAndSellRepository buyAndSellRepository;

    @Override
    @Transactional
    public Long postBuyAndSell(PostBuyAndSellCommand command) {

        // Todo 이미지 순서 관련 기획 논의
        // Todo 임시저장 관련 기획
        List<String> objectNames = imageCommand.saveImages(
                new SaveImagesCommand(command.getImageFiles(), BUY_AND_SELL.getBucket())
        );
        List<String> imageUrls = objectUrlMapper.toUrl(objectNames, MinioBucket.ARTICLE_TRADE);
        BuyAndSell buyAndSell = BuyAndSell.write(command, imageUrls);
        postArticlePort.postBuyAndSell(buyAndSell);
        return buyAndSell.getId();
    }

    @Override
    @Transactional
    public Long updateBuyAndSell(UpdateBuyAndSellCommand command) {
        BuyAndSell buyAndSell = articleQueryPort.getBuyAndSell(command.getArticleId())
                .orElseThrow(() -> new NotFoundException(BuyAndSell.class));
        List<String> imageUrls = buyAndSell.getImageUrls();

        if(command.getImageFiles() != null) {
            List<String> objectNames = imageCommand.saveImages(
                    new SaveImagesCommand(command.getImageFiles(), BUY_AND_SELL.getBucket())
            );
            imageUrls = objectUrlMapper.toUrl(objectNames, MinioBucket.ARTICLE_TRADE);
        }

        buyAndSell.update(command, imageUrls);
        return buyAndSell.getId();
    }

    @Override
    @Transactional
    public Long delete(Long articleId) {
        BuyAndSell buyAndSell = articleQueryPort.getBuyAndSell(articleId)
                .orElseThrow(() -> new NotFoundException(BuyAndSell.class));
        buyAndSell.delete();
        return buyAndSell.getId();
    }
}
