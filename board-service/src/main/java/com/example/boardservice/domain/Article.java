package com.example.boardservice.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@SuperBuilder
public class Article {

    private Long articleId;

    private Member member;

    private String title;

    private String content;

    private Integer viewCount;

    private List<String> imageUrls;

    private List<String> hashtagList;

    private ArticleType type;

    private LocalDateTime createdAt;

    private int likeCount;

    private Boolean isLiked;

    private int commentCount;

    private Boolean isEditable;

    public void addHashtagList(List<String> hashtagList) {
        this.hashtagList = hashtagList;
    }

    public void addLikeStatus(boolean isLiked) {
        this.isLiked = isLiked;
    }

    public void addMember(Member member) {
        this.member = member;
    }

    public static List<Long> getAuthorId(List<Article> articleList) {
        return articleList.stream()
                .map(Article::getAuthorIdFromMember)
                .collect(Collectors.toList());
    }

    public void addEditable(Long currentMemberId) {
        this.isEditable = Objects.equals(this.member.getMemberId(), currentMemberId);
    }

    /**
     * private
     */
    private Long getAuthorIdFromMember() {
        return this.member.getMemberId();
    }

}
