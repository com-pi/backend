package com.example.boardservice.adapter.in.web.request;

import com.example.boardservice.adapter.out.persistence.converter.LocationToPointConverter;
import com.example.boardservice.domain.BuyAndSell;
import com.example.boardservice.domain.Location;
import com.example.boardservice.util.validator.FreePriceValid;
import com.example.common.baseentity.SelfValidating;
import com.example.common.domain.Address;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@FreePriceValid
public class UpdateBuyAndSellRequest extends SelfValidating<UpdateBuyAndSellRequest> {

    private final Long articleId;
    @Setter
    private Long memberId;
    private final String title;
    private final String content;
    private final Integer price;
    private final Location location;
    private final String sido;
    private final String sigungu;
    private final String eupmyundong;
    private final Boolean isFree;
    private final List<String> hashTags;

    public UpdateBuyAndSellRequest(Long articleId, String title, String content, Integer price,
                                   Location location, String sido, String sigungu, String eupmyundong, Boolean isFree,
                                   List<String> hashTags) {
        this.articleId = articleId;
        this.title = title;
        this.content = content;
        this.price = price;
        this.location = location;
        this.sido = sido;
        this.sigungu = sigungu;
        this.eupmyundong = eupmyundong;
        this.isFree = isFree;
        this.hashTags = hashTags;
        validateSelf();
    }


    public BuyAndSell toDomain() {
        return BuyAndSell.builder()
                .articleId(articleId)
                .memberId(memberId)
                .title(title)
                .content(content)
                .price(price)
                .location(new LocationToPointConverter().convertToDatabaseColumn(location))
                .area(Address.of(sido, sigungu, eupmyundong))
                .isFree(isFree)
                .hashtags(hashTags)
                .build();
    }
}