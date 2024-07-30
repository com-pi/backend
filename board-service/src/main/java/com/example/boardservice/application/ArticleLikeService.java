package com.example.boardservice.application;

import com.example.boardservice.application.port.in.ArticleLikeUseCase;
import com.example.boardservice.application.port.out.CommonArticleCommandPort;
import com.example.boardservice.application.port.out.ArticleLikeCommandPort;
import com.example.boardservice.application.port.out.ArticleLikeQueryPort;
import com.example.boardservice.domain.ArticleLike;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleLikeService implements ArticleLikeUseCase {

    private final ArticleLikeCommandPort articleLikeCommandPort;
    private final ArticleLikeQueryPort articleLikeQueryPort;
    private final CommonArticleCommandPort articleCommandPort;

    @Override
    @Transactional
    public Long like(ArticleLike like) {
        articleLikeQueryPort.findLikeByArticleIdAndMemberId(like.getArticleId(), like.getMemberId())
                .ifPresent(originLike -> {
                    originLike.validateIfAlreadyLiked();
                    like.updateLikeId(originLike.getLikeId());
                });

        like.like();
        ArticleLike savedLike = articleLikeCommandPort.save(like);
        articleCommandPort.increaseLikeCount(savedLike.getArticleId());
        return savedLike.getLikeId();
    }

    @Override
    @Transactional
    public Long unlike(ArticleLike like) {
        ArticleLike originLike = articleLikeQueryPort.getLikeByArticleIdAndMemberId(like.getArticleId(), like.getMemberId());
        like.validatePermission(originLike.getMemberId());
        like.validateStatus(originLike.isLiked());

        originLike.unlike();
        articleLikeCommandPort.save(originLike);
        articleCommandPort.decreaseLikeCount(originLike.getArticleId());
        return like.getLikeId();
    }

    @Override
    public ArticleLike getLikeStatusByCurrentMember(Long articleId, Long memberId) {
        return articleLikeQueryPort.findLikeByArticleIdAndMemberIdAndIsLiked(articleId, memberId, true)
                .map(like -> ArticleLike.ofStatus(like.getLikeId(), true))
                .orElse(ArticleLike.ofStatus(null, false));
    }

    public List<Boolean> getLikeStatusByArticleList(List<Long> articleIdList, Long memberId) {
        List<ArticleLike> likeList =  articleLikeQueryPort.getLikeByArticleList(articleIdList, memberId);
        Map<Long, Boolean> likeStatus = likeList.stream()
                .collect(Collectors.toMap(ArticleLike::getArticleId, ArticleLike::isLiked));
        return articleIdList.stream()
                .map(articleId -> likeStatus.getOrDefault(articleId, false))
                .collect(Collectors.toList());
    }

    public boolean getLikeStatusByArticle(Long articleId, Long memberId) {
        return articleLikeQueryPort.findLikeByArticleIdAndMemberId(articleId, memberId)
                .map(ArticleLike::getIsLiked)
                .orElse(false);
    }
}
