package com.example.boardservice.application;

import com.example.boardservice.application.port.in.FindArticleQuery;
import com.example.boardservice.domain.BuyAndSell;

import java.util.Optional;

public class FindArticleService implements FindArticleQuery {

    @Override
    public Optional<BuyAndSell> findBuyAndSellById(Long id) {
        return Optional.empty();
    }

}
