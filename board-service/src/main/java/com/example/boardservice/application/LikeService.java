package com.example.boardservice.application;

import com.example.boardservice.application.port.in.LikeUseCase;
import com.example.boardservice.application.port.out.CommonArticleCommandPort;
import com.example.boardservice.application.port.out.LikeCommandPort;
import com.example.boardservice.application.port.out.LikeQueryPort;
import com.example.boardservice.domain.Article;
import com.example.boardservice.domain.Like;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeService implements LikeUseCase {

    private final LikeCommandPort likeCommandPort;
    private final LikeQueryPort likeQueryPort;
    private final CommonArticleCommandPort articleCommandPort;

    @Override
    @Transactional
    public Long like(Like like) {
        likeQueryPort.findLikeByArticleIdAndMemberId(like.getArticleId(), like.getMemberId())
                .ifPresent(originLike -> {
                    originLike.validateIfAlreadyLiked();
                    like.updateLikeId(originLike.getLikeId());
                });

        like.like();
        Like savedLike = likeCommandPort.save(like);
        articleCommandPort.increaseLikeCount(savedLike.getArticleId());
        return savedLike.getLikeId();
    }

    @Override
    @Transactional
    public Long unlike(Like like) {
        Like originLike = likeQueryPort.getLikeByLikeId(like.getLikeId());
        like.validatePermission(originLike.getMemberId());
        like.validateStatus(originLike.isLiked());

        originLike.unlike();
        likeCommandPort.save(originLike);
        articleCommandPort.decreaseLikeCount(originLike.getArticleId());
        return like.getLikeId();
    }

    @Override
    public Like getLikeStatusByCurrentMember(Long articleId, Long memberId) {
        return likeQueryPort.findLikeByArticleIdAndMemberIdAndIsLiked(articleId, memberId, true)
                .map(like -> Like.ofStatus(like.getLikeId(), true))
                .orElse(Like.ofStatus(null, false));
    }

    public List<Boolean> getLikeStatusByArticleList(List<Long> articleIdList, Long memberId) {
        List<Like> likeList =  likeQueryPort.getLikeByArticleList(articleIdList, memberId);
        Map<Long, Boolean> likeStatus = likeList.stream()
                .collect(Collectors.toMap(Like::getArticleId, Like::isLiked));
        return articleIdList.stream()
                .map(articleId -> likeStatus.getOrDefault(articleId, false))
                .collect(Collectors.toList());
    }

    public boolean getLikeStatusByArticle(Long articleId, Long memberId) {
        return likeQueryPort.findLikeByArticleIdAndMemberId(articleId, memberId)
                .map(Like::getIsLiked)
                .orElse(false);
    }
}
