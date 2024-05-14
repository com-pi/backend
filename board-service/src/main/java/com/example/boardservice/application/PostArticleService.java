package com.example.boardservice.application;

import com.example.boardservice.adapter.in.web.PostBuyAndSellCommand;
import com.example.boardservice.application.port.in.PostArticleUseCase;
import com.example.boardservice.application.port.out.ImageCommandPort;
import com.example.boardservice.application.port.out.PostArticlePort;
import com.example.boardservice.application.port.out.SaveImagesCommand;
import com.example.boardservice.domain.Address;
import com.example.boardservice.domain.BuyAndSell;
import com.example.boardservice.domain.Member;
import com.example.boardservice.util.ObjectUrlMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.boardservice.domain.ArticleType.BUY_AND_SELL;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostArticleService implements PostArticleUseCase {

    private final ImageCommandPort imageCommandPort;
    private final PostArticlePort postArticlePort;
    private final ObjectUrlMapper objectUrlMapper;

    @Override
    @Transactional
    public void postBuyAndSell(PostBuyAndSellCommand command) {

        // Todo 이미지 순서 관련 기획 논의
        // Todo 임시저장 관련 기획
        List<String> objectNames = imageCommandPort.saveImages(
                new SaveImagesCommand(command.getImageFiles(), BUY_AND_SELL)
        );

        BuyAndSell newPost = BuyAndSell.builder()
                .member(Member.ofId(command.getMemberId()))
                .title(command.getTitle())
                .content(command.getContent())
                .price(command.getPrice())
                .area(Address.of(command.getSido(), command.getSigungu(), command.getEupmyundong()))
                .imageUrls(objectUrlMapper.toUrl(objectNames))
                .hashtags(command.getHashTags())
                .articleType(BUY_AND_SELL)
                .build();

        postArticlePort.postBuyAndSell(newPost);
    }

}
