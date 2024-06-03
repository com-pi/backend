package com.example.boardservice.application;

import com.example.boardservice.adapter.in.web.response.BuyAndSellDetailResponse;
import com.example.boardservice.adapter.in.web.response.BuyAndSellListResponse;
import com.example.boardservice.adapter.in.web.response.BuyAndSellResponse;
import com.example.boardservice.application.port.in.ReadArticleUseCase;
import com.example.boardservice.application.port.out.ArticleQueryPort;
import com.example.boardservice.domain.BuyAndSell;
import com.example.boardservice.security.PassportHolder;
import com.example.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadArticleService implements ReadArticleUseCase {

    private final ArticleQueryPort articleQueryPort;


    @Override
    public BuyAndSellListResponse getBuyAndSellList(int page) {
        Page<BuyAndSell> buyAndSellPage = articleQueryPort.getBuyAndSellList(page);
        List<BuyAndSellResponse> buyAndSellResponseList = buyAndSellPage.stream()
                .map(BuyAndSellResponse::from)
                .toList();

        return BuyAndSellListResponse.of(buyAndSellResponseList);
    }

    @Override
    @Transactional
    public BuyAndSellDetailResponse getBuyAndSell(Long id) {
        BuyAndSell buyAndSell = articleQueryPort.getBuyAndSell(id)
                .orElseThrow(() -> new NotFoundException(BuyAndSell.class));

        incrementViewCount(buyAndSell);
        return BuyAndSellDetailResponse.of(buyAndSell);
    }

    public void incrementViewCount(BuyAndSell buyAndSell) {
        Long currentMemberId = PassportHolder.getPassport().memberId();
        if(!buyAndSell.getMember().isSameAs(currentMemberId)) {
            buyAndSell.incrementViewCount();
        }
    }
}
