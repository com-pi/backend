package com.example.boardservice.domain;

import com.example.boardservice.adapter.out.persistence.JsonToStringListConverter;
import com.example.common.baseentity.DeletedAtAbstractEntity;
import com.example.common.domain.Address;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.util.List;

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

    @Convert(converter = JsonToStringListConverter.class)
    private List<String> imageUrls;

    @Convert(converter = JsonToStringListConverter.class)
    private List<String> hashtags;

}
