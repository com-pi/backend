package com.example.boardservice.application;

import com.example.boardservice.application.port.in.LikeUseCase;
import com.example.boardservice.application.port.out.ArticleQueryPort;
import com.example.boardservice.application.port.out.LikeCommandPort;
import com.example.boardservice.application.port.out.LikeQueryPort;
import com.example.boardservice.domain.Article;
import com.example.boardservice.domain.Like;
import com.example.common.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeService implements LikeUseCase {

    private final LikeCommandPort likeCommandPort;
    private final LikeQueryPort likeQueryPort;
    private final ArticleQueryPort articleQueryPort;

    @Override
    @Transactional
    public Long like(Like like) {
        return likeQueryPort.findLikeByArticleIdAndMemberId(like.getArticleId(), like.getMemberId())
                .map(originLike -> {
                    originLike.like();
                    likeCommandPort.save(originLike);
                    return originLike.getLikeId();
                })
                .orElseGet(() -> {
                    Article article = articleQueryPort.getArticleById(like.getArticleId());
                    like.generate(article.getArticleType());
                    return likeCommandPort.save(like).getLikeId();
                });
    }

    @Override
    @Transactional
    public Long unlike(Like like) {
        Like originLike = likeQueryPort.getLikeByLikeId(like.getLikeId());
        validatePermission(like.getMemberId(), originLike.getMemberId());

        originLike.unlike();
        likeCommandPort.save(originLike);
        return like.getLikeId();
    }

    @Override
    public Like getLikeStatusByCurrentMember(Long articleId, Long memberId) {
        return likeQueryPort.findLikeByArticleIdAndMemberIdAndIsLiked(articleId, memberId, true)
                .map(like -> Like.ofStatus(like.getLikeId(), true))
                .orElse(Like.ofStatus(null, false));
    }

    private void validatePermission(Long originMemberId, Long memberId) {
        if(!Objects.equals(originMemberId, memberId)) {
            throw new UnauthorizedException("게시글에 찜을 해제할 권한이 없습니다.");
        }
    }
}
