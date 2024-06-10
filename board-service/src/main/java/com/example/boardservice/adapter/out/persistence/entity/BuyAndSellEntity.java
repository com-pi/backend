package com.example.boardservice.adapter.out.persistence.entity;

import com.example.boardservice.domain.ArticleType;
import com.example.boardservice.domain.BuyAndSell;
import com.example.common.domain.Address;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@DiscriminatorValue("buy_and_sell")
@Table(name = "buy_and_sell")
public class BuyAndSellEntity extends CommonArticleEntity {

    private Integer price;

    // 평면 좌표계 지리데이터
    @Column(columnDefinition = "Geometry(Point, 4326)")
    private Point location;

    @Embedded
    private Address area;

    private Integer likeCount;

    private Boolean isFree;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private List<ArticleHashtagEntity> articleHashtags = new ArrayList<>();


    /**
     * 생성자
     */
    @Builder
    public BuyAndSellEntity(Long articleId, MemberEntity member, String title, String content,
                            Integer viewCount, List<String> imageUrls, Integer price, Point location,
                            Address area, Integer likeCount, Boolean isFree, List<ArticleHashtagEntity> articleHashtags) {
        super(articleId, member, title, content, viewCount, imageUrls, ArticleType.BUY_AND_SELL);
        this.price = price;
        this.location = location;
        this.area = area;
        this.likeCount = likeCount;
        this.isFree = isFree;
        this.articleHashtags = articleHashtags;
    }

    /**
     *
     */
    @Override
    public void delete() {
        super.delete();
    }

    public static BuyAndSellEntity from(BuyAndSell buyAndSell) {
        return BuyAndSellEntity.builder()
                .articleId(buyAndSell.getArticleId())
                .member(MemberEntity.from(buyAndSell.getMemberId()))
                .title(buyAndSell.getTitle())
                .content(buyAndSell.getContent())
                .price(buyAndSell.getPrice())
                .location(buyAndSell.getLocation())
                .area(buyAndSell.getArea())
                .viewCount(buyAndSell.getViewCount())
                .isFree(buyAndSell.getIsFree())
                .imageUrls(buyAndSell.getImageUrls())
                .articleHashtags(buyAndSell.getArticleHashtags().stream()
                        .map(boardHashtag -> ArticleHashtagEntity.ofId(boardHashtag.getArticleHashtagId())).toList())
                .build();
    }

    public BuyAndSell toDomain() {
        return BuyAndSell.builder()
                .articleId(this.articleId)
                .memberId(this.member.getMemberId())
                .title(this.title)
                .content(this.content)
                .price(this.price)
                .location(this.location)
                .area(this.area)
                .viewCount(this.viewCount)
                .likeCount(this.likeCount)
                .isFree(this.isFree)
                .imageUrls(this.imageUrls)
                .articleHashtags(this.articleHashtags.stream().map(ArticleHashtagEntity::toDomain).toList())
                .build();
    }
}
