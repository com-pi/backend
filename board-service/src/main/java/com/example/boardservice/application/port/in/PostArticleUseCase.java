package com.example.boardservice.application.port.in;

import com.example.boardservice.adapter.in.web.PostBuyAndSellCommand;

public interface PostArticleUseCase {

    Long postBuyAndSell(PostBuyAndSellCommand command);

}
