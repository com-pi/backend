package com.example.boardservice.adapter.in.web.request;

import com.example.boardservice.domain.BuyAndSell;
import lombok.Getter;
import lombok.Setter;

@Getter
public class DeleteBuyAndSellRequest {

    private final Long articleId;
    @Setter
    private Long memberId;

    public DeleteBuyAndSellRequest(Long articleId) {
        this.articleId = articleId;
    }

    public static DeleteBuyAndSellRequest of(Long articleId) {
        return new DeleteBuyAndSellRequest(articleId);
    }

    public BuyAndSell toDomain() {
        return BuyAndSell.builder()
                .articleId(articleId)
                .memberId(memberId)
                .build();
    }
}
