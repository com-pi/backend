package com.example.boardservice.domain;

import com.example.boardservice.adapter.out.persistence.JsonToStringListConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "article")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "generate")
public class BuyAndSell {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private ArticleType articleType;

    private String title;
    private String content;
    private Integer price;

    @Embedded
    private Area area;

    @Convert(converter = JsonToStringListConverter.class)
    private List<String> imageUrls;

    @Convert(converter = JsonToStringListConverter.class)
    private List<String> hashtags;

}
