package com.example.boardservice.domain;

import com.example.boardservice.adapter.out.persistence.converter.JsonToStringListConverter;
import com.example.common.domain.Address;
import jakarta.persistence.Convert;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BuyAndSellCreate {

    private Long articleId;

    private Long memberId;

    private String title;

    private String content;

    private Integer price;

    private Point location;

    private Address area;

    private Integer viewCount;

    private Integer likeCount;

    private Boolean isFree;

    @Convert(converter = JsonToStringListConverter.class)
    private List<String> imageUrls;

    @Convert(converter = JsonToStringListConverter.class)
    private List<String> hashtagNameList;
}