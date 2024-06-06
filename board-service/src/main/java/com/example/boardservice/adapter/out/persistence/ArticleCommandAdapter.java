package com.example.boardservice.adapter.out.persistence;

import com.example.boardservice.application.port.out.PostArticlePort;
import com.example.boardservice.application.port.out.UpdateArticlePort;
import com.example.boardservice.domain.BuyAndSell;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArticleCommandAdapter implements PostArticlePort, UpdateArticlePort {

    private final BuyAndSellRepository buyAndSellRepository;

    @Override
    public void postBuyAndSell(BuyAndSell buyAndSell) {
        buyAndSellRepository.save(buyAndSell);
    }
}
