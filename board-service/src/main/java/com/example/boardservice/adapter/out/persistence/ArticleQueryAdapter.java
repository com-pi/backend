package com.example.boardservice.adapter.out.persistence;

import com.example.boardservice.application.port.out.ArticleQueryPort;
import com.example.boardservice.domain.BuyAndSell;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ArticleQueryAdapter implements ArticleQueryPort {

    private final BuyAndSellRepository buyAndSellRepository;

    @Override
    public Page<BuyAndSell> getBuyAndSellList(int page) {
        return buyAndSellRepository.findByDeletionYn("N", PageRequest.of(page, 10));
    }

    @Override
    public Optional<BuyAndSell> getBuyAndSell(Long id) {
        return buyAndSellRepository.findById(id);
    }
}
