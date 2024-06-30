package com.example.boardservice.adapter.out.persistence;

import com.example.boardservice.adapter.out.persistence.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    Optional<CommentEntity> findByCommentIdAndDeletionYn(Long commentId, String deletionYn);

    List<CommentEntity> findByArticle_ArticleIdAndDeletionYn(Long articleId, String deleteionYn);
}
