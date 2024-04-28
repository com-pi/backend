package com.example.boardservice.adapter.out.persistence;

import com.example.boardservice.application.port.in.FindArticleQuery;
import com.example.boardservice.domain.BuyAndSell;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ArticleQueryAdapter implements FindArticleQuery {

    private final BuyAndSellRepository buyAndSellRepository;

    @Override
    public Optional<BuyAndSell> findBuyAndSellById(Long id) {
        return buyAndSellRepository.findById(id);
    }
}
