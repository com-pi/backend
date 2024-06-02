package com.example.boardservice.application.port.in;

import com.example.boardservice.adapter.in.web.command.PostBuyAndSellCommand;
import com.example.boardservice.adapter.in.web.command.UpdateBuyAndSellCommand;

public interface ArticleUseCase {

    Long postBuyAndSell(PostBuyAndSellCommand command);

    Long updateBuyAndSell(UpdateBuyAndSellCommand command);

    Long delete(Long articleId);
}
