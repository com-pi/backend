package com.example.boardservice.adapter.out.persistence;

import com.example.boardservice.adapter.out.persistence.entity.CommentLikeEntity;
import com.example.boardservice.application.port.out.CommentLikeQueryPort;
import com.example.boardservice.domain.CommentLike;
import com.example.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CommentLikeQueryAdapter implements CommentLikeQueryPort {

    private final CommentLikeRepository commentLikeRepository;


    @Override
    public Optional<CommentLike> findLike(Long commentId, Long memberId) {
        Optional<CommentLikeEntity> likeEntity = commentLikeRepository.findByComment_CommentIdAndMemberId(commentId, memberId);
        return likeEntity.map(CommentLikeEntity::toDomain);
    }

    @Override
    public CommentLike getLike(Long commentId, Long memberId) {
        CommentLikeEntity likeEntity = commentLikeRepository.findByComment_CommentIdAndMemberId(commentId, memberId)
                .orElseThrow(() -> new NotFoundException(CommentLikeEntity.class));
        return likeEntity.toDomain();
    }

    @Override
    public List<CommentLike> getLikeByCommentList(List<Long> commentIdList, Long memberId) {
        List<CommentLikeEntity> likeEntityList =  commentLikeRepository.findByComment_CommentIdInAndMemberId(commentIdList, memberId);
        return likeEntityList.stream()
                .map(CommentLikeEntity::toDomain)
                .toList();
    }
}
