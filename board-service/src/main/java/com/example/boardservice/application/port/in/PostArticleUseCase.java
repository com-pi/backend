package com.example.boardservice.application.port.in;

import com.example.boardservice.adapter.in.web.PostBuyAndSellRequest;

public interface PostArticleUseCase {

    void postBuyAndSell(PostBuyAndSellRequest command);

}
