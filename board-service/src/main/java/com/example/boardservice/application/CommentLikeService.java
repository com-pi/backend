package com.example.boardservice.application;

import com.example.boardservice.application.port.in.CommentLikeUseCase;
import com.example.boardservice.application.port.out.CommentCommandPort;
import com.example.boardservice.application.port.out.CommentLikeCommandPort;
import com.example.boardservice.application.port.out.CommentLikeQueryPort;
import com.example.boardservice.domain.CommentLike;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentLikeService implements CommentLikeUseCase {

    private final CommentLikeCommandPort commentLikeCommandPort;
    private final CommentLikeQueryPort commentLikeQueryPort;
    private final CommentCommandPort commentCommandPort;

    @Override
    @Transactional
    public Long like(CommentLike like) {
        commentLikeQueryPort.findLike(like.getCommentId(), like.getMemberId())
                .ifPresent(originLike -> {
                    originLike.validateIfAlreadyLiked();
                    like.updateLikeId(originLike.getLikeId());
                });

        like.like();
        CommentLike savedLike = commentLikeCommandPort.save(like);
        commentCommandPort.increaseLikeCount(savedLike.getCommentId());
        return savedLike.getLikeId();
    }

    @Override
    @Transactional
    public Long unlike(CommentLike like) {
        CommentLike originLike = commentLikeQueryPort.getLike(like.getCommentId(), like.getMemberId());
        like.validatePermission(originLike.getMemberId());
        like.validateStatus(originLike.isLiked());

        originLike.unlike();
        commentLikeCommandPort.save(originLike);
        commentCommandPort.decreaseLikeCount(originLike.getCommentId());
        return like.getLikeId();
    }


}
