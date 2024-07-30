package com.example.boardservice.adapter.out.persistence;

import com.example.boardservice.adapter.out.persistence.entity.CommentLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLikeEntity, Long> {

    Optional<CommentLikeEntity> findByComment_CommentIdAndMemberId(Long commentId, Long memberId);

    List<CommentLikeEntity> findByComment_CommentIdInAndMemberId(List<Long> commentIdList, Long memberId);
}
