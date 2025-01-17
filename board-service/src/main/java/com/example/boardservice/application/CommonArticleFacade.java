package com.example.boardservice.application;

import com.example.boardservice.adapter.in.web.command.GetArticleListCommand;
import com.example.boardservice.adapter.in.web.command.GetSearchedArticleListCommand;
import com.example.boardservice.application.port.in.CommonArticleUseCase;
import com.example.boardservice.domain.Article;
import com.example.boardservice.domain.ArticleHashtag;
import com.example.boardservice.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CommonArticleFacade implements CommonArticleUseCase {

    private final CommonArticleService articleService;
    private final ArticleHashtagService articleHashtagService;
    private final ArticleLikeService likeService;
    private final MemberService memberService;

    public List<Article> getArticleList(GetArticleListCommand command) {
        Page<Article> articlePage = articleService.getArticleList(command.getType(), command.getPageable());
        articlePage.forEach(this::addHashtags);
        addLikeStatusList(articlePage.getContent(), command.getMemberId());
        addMember(articlePage.getContent());
        return articlePage.toList();
    }

    @Override
    public List<Article> getArticleListByHashtag(String name, String type, Pageable pageable) {
        Long hashtagId = articleHashtagService.getHashtagIdByName(name);

        if(Objects.isNull(hashtagId)) {
            return Collections.emptyList();
        }

        List<Long> articleIdList = articleHashtagService.getArticleIdByHashtagId(hashtagId, pageable).stream()
                .map(ArticleHashtag::getArticleId)
                .toList();

        List<Article> articleList = articleService.getArticleListByArticleId(articleIdList, type, pageable);

        articleList.forEach(this::addHashtags);
        addMember(articleList);
        return articleList;
    }

    public Article getArticle(Article article) {
        Article originArticle = articleService.getArticle(article.getArticleId());
        addHashtags(originArticle);
        addLikeStatus(originArticle, article.getMember().getMemberId());
        addMember(List.of(originArticle));
        return originArticle;
    }

    @Override
    public List<Article> searchArticleList(GetSearchedArticleListCommand command) {
        Page<Article> articlePage = Objects.isNull(command.getKeyword()) ?
                articleService.getArticleList(command.getType(), command.getPageable())
                : articleService.searchArticleList(command.getKeyword(), command.getType(), command.getPageable());
        articlePage.forEach(this::addHashtags);
        addLikeStatusList(articlePage.getContent(), command.getMemberId());
        addMember(articlePage.getContent());
        return articlePage.toList();
    }

    @Override
    public List<Article> getArticleListByMember(Long memberId, Pageable pageable) {
        Page<Article> articlePage = articleService.getArticleListByMemberId(memberId, pageable);
        articlePage.forEach(this::addHashtags);
        addLikeStatusList(articlePage.getContent(), memberId);
        addMember(articlePage.getContent());
        return articlePage.toList();
    }

    @Override
    public List<Article> getLikedArticleListByMember(Long memberId, Pageable pageable) {
        Page<Article> articlePage = articleService.getLikedArticleId(memberId, pageable);
        articlePage.forEach(this::addHashtags);
        addLikeStatusList(articlePage.getContent());
        addMember(articlePage.getContent());
        return articlePage.toList();
    }


    /**
     * private
     */
    private void addHashtags(Article article) {
        List<ArticleHashtag> articleHashtags = articleHashtagService.getHashtagListByArticle(article.getArticleId());
        List<String> hashtagList = articleHashtags.stream()
                .map(articleHashtag -> articleHashtag.getHashtag().getName())
                .toList();
        article.addHashtagList(hashtagList);
    }

    private void addLikeStatusList(List<Article> articleList, Long memberId) {
        List<Long> articleIdList = articleList.stream()
                .map(Article::getArticleId)
                .toList();

        List<Boolean> likeStatus = likeService.getLikeStatusByArticleList(articleIdList, memberId);
        for (int i = 0; i < articleList.size(); i++) {
            articleList.get(i).addLikeStatus(likeStatus.get(i));
        }
    }

    private void addLikeStatus(Article article, Long memberId) {
        boolean likeStatus = likeService.getLikeStatusByArticle(article.getArticleId(), memberId);
        article.addLikeStatus(likeStatus);
    }

    private void addLikeStatusList(List<Article> articleList) {
        articleList.forEach(article -> {
            article.addLikeStatus(true);
        });
    }

    private void addMember(List<Article> articleList) {
        List<Long> authorIdList = Article.getAuthorId(articleList);
        List<Member> memberList = memberService.getMemberList(authorIdList);
        for (int i = 0; i < articleList.size(); i++) {
            articleList.get(i).addMember(memberList.get(i));
        }
    }

}
