package com.example.boardservice.domain;

import com.example.boardservice.adapter.in.web.command.PostBuyAndSellCommand;
import com.example.boardservice.adapter.in.web.command.UpdateBuyAndSellCommand;
import com.example.boardservice.adapter.out.persistence.converter.JsonToStringListConverter;
import com.example.boardservice.adapter.out.persistence.converter.LocationToPointConverter;
import com.example.common.baseentity.DeletedAtAbstractEntity;
import com.example.common.domain.Address;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.util.List;

import static com.example.boardservice.domain.ArticleType.BUY_AND_SELL;

@Entity
@Table(name = "article")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BuyAndSell extends DeletedAtAbstractEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private ArticleType articleType;

    private String title;

    private String content;

    private Integer price;

    // 평면 좌표계 지리데이터
    @Column(columnDefinition = "Geometry(Point, 4326)")
    private Point location;

    @Embedded
    private Address area;

    private Integer viewCount;

    @Convert(converter = JsonToStringListConverter.class)
    private List<String> imageUrls;

    @Convert(converter = JsonToStringListConverter.class)
    private List<String> hashtags;

    public static BuyAndSell write(PostBuyAndSellCommand command, List<String> imageUrls) {
        return BuyAndSell.builder()
                .member(Member.ofId(command.getMemberId()))
                .title(command.getTitle())
                .content(command.getContent())
                .price(command.getPrice())
                .location(new LocationToPointConverter().convertToDatabaseColumn(command.getLocation()))
                .area(Address.of(command.getSido(), command.getSigungu(), command.getEupmyundong()))
                .viewCount(0)
                .imageUrls(imageUrls)
                .hashtags(command.getHashTags())
                .articleType(BUY_AND_SELL)
                .build();
    }

    public void update(UpdateBuyAndSellCommand command, List<String> imageUrls) {
        this.title = command.getTitle();
        this.content = command.getContent();
        this.price = command.getPrice();
        this.location = new LocationToPointConverter().convertToDatabaseColumn(command.getLocation());
        this.area = Address.of(command.getSido(), command.getSigungu(), command.getEupmyundong());
        this.hashtags = command.getHashTags();
        this.imageUrls = imageUrls;
    }

    @Override
    public void delete() {
        super.delete();
    }

    public void incrementViewCount() {
        this.viewCount++;
    }
}
