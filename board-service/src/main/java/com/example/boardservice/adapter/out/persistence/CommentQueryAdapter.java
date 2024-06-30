package com.example.boardservice.adapter.out.persistence;

import com.example.boardservice.adapter.out.persistence.entity.CommentEntity;
import com.example.boardservice.application.port.out.CommentQueryPort;
import com.example.boardservice.domain.Comment;
import com.example.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CommentQueryAdapter implements CommentQueryPort {

    private final CommentRepository commentRepository;

    @Override
    public Comment getComment(Long commentId) {
        CommentEntity commentEntity = commentRepository.findByCommentIdAndDeletionYn(commentId, "N")
                .orElseThrow(() -> new NotFoundException(CommentEntity.class));
        return commentEntity.toDomain();
    }

    @Override
    public List<Comment> getCommentList(Long articleId) {
        List<CommentEntity> commentEntity = commentRepository.findByArticle_ArticleIdAndDeletionYn(articleId, "N");
        return commentEntity.stream()
                .map(CommentEntity::toDomain)
                .toList();
    }
}
